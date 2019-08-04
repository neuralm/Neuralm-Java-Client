package net.neuralm.client;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("ALL")
public class AsynchronousSSLChannel {
  private final ExecutorService executor = Executors.newSingleThreadExecutor();
  private final AsynchronousSocketChannel channel;
  private final SSLEngine engine;
  private final SSLSession session;
  private final String serverAddress;
  private final int port;

  private ByteBuffer appData;
  private ByteBuffer netData;
  private ByteBuffer peerAppData;
  private ByteBuffer peerNetData;

  protected AsynchronousSSLChannel(String protocol, String serverAddress, int port) throws Exception {
    this.channel = AsynchronousSocketChannel.open();
    this.serverAddress = serverAddress;
    this.port = port;

    SSLContext context = SSLContext.getInstance(protocol);
    context.init(
            createKeyManagers("./src/main/resources/client.jks", "storepass", "keypass"),
            createTrustManagers("./src/main/resources/trustedCerts.jks", "storepass"),
            new SecureRandom());
    engine = context.createSSLEngine(serverAddress, port);
    engine.setUseClientMode(true);

    session = engine.getSession();
    appData = ByteBuffer.allocate(1024);
    netData = ByteBuffer.allocate(session.getPacketBufferSize());
    peerAppData = ByteBuffer.allocate(1024);
    peerNetData = ByteBuffer.allocate(session.getPacketBufferSize());
  }

  public <A> void connect(A attachment, CompletionHandler<Void, ? super A> handler) {
    channel.connect(new InetSocketAddress(serverAddress, port), attachment, new CompletionHandler<Void, A>() {
      @Override
      public void completed(Void result, A attachment) {
        try {
          doHandShake();
          handler.completed(result, attachment);
        } catch (SSLException | ExecutionException | InterruptedException e) {
          handler.failed(e, attachment);
        }
      }

      @Override
      public void failed(Throwable exc, A attachment) {
        handler.failed(exc, attachment);
      }
    });
  }

  public <A> void read(ByteBuffer dst, A attachment, CompletionHandler<Integer, ? super A> handler) throws ExecutionException, InterruptedException, IOException {
    System.out.println("About to read from the server...");
    peerNetData.clear();
    channel.read(peerNetData, attachment, new CompletionHandler<Integer, A>() {
      @Override
      public void completed(Integer result, A attachment) {
        try {
          if (result > 0) {
            peerNetData.flip();
            while (peerNetData.hasRemaining()) {
              peerAppData.clear();
              SSLEngineResult sslEngineResult = sslEngineResult = engine.unwrap(peerNetData, peerAppData);
              switch (sslEngineResult.getStatus()) {
                case OK:
                  peerAppData.flip();
                  System.out.println("Message received from server!");
                  handler.completed(result, attachment);
                  break;
                case BUFFER_OVERFLOW:
                  peerAppData = enlargeBuffer(peerAppData, session.getApplicationBufferSize());
                  break;
                case BUFFER_UNDERFLOW:
                  peerNetData = handleBufferUnderflow(session, peerNetData);
                  break;
                case CLOSED:
                  close();
                  return;
                default:
                  throw new IllegalStateException("Invalid SSL status: " + sslEngineResult.getStatus());
              }
            }
          } else if (result < 0) {
              handleEndOfStream();
          }
        } catch (Exception e) {
          handler.failed(e, attachment);
        }
      }

      @Override
      public void failed(Throwable exc, A attachment) {
        handler.failed(exc, attachment);
      }
    });
  }

  public <A> void write(ByteBuffer src, A attachment, CompletionHandler<Integer, ? super A> handler) throws IOException, InterruptedException, ExecutionException {
    System.out.println("About to write to the server...");
    appData.clear();
    appData.put(src);
    appData.flip();
    while (appData.hasRemaining()) {
      // The loop has a meaning for (outgoing) messages larger than 16KB.
      // Every wrap call will remove 16KB from the original message and send it to the remote peer.
      netData.clear();
      SSLEngineResult result = engine.wrap(appData, netData);
      switch (result.getStatus()) {
        case OK:
          netData.flip();
          while (netData.hasRemaining()) {
           channel.write(netData);
          }
          System.out.println("Message sent to the server:");
          break;
        case BUFFER_OVERFLOW:
          netData = enlargeBuffer(netData, session.getPacketBufferSize());
          break;
        case BUFFER_UNDERFLOW:
          throw new SSLException("Buffer underflow occurred after a wrap. I don't think we should ever get here.");
        case CLOSED:
          close();
          return;
        default:
          throw new IllegalStateException("Invalid SSL status: " + result.getStatus());
      }
    }
  }

  public SocketAddress getLocalAddress() throws IOException {
    return channel.getLocalAddress();
  }

  public SocketAddress getRemoteAddress() throws IOException {
    return channel.getRemoteAddress();
  }

  public boolean isOpen() {
    return channel.isOpen();
  }

  public void close() throws IOException, ExecutionException, InterruptedException {
    engine.closeOutbound();
    doHandShake();
    channel.close();
    executor.shutdown();
  }

  private static KeyManager[] createKeyManagers(String filepath, String keystorePassword, String keyPassword) throws Exception {
    KeyStore keyStore = getKeyStore(filepath, keystorePassword);
    KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    kmf.init(keyStore, keyPassword.toCharArray());
    return kmf.getKeyManagers();
  }

  private static TrustManager[] createTrustManagers(String filePath, String keyStorePassword) throws Exception {
    KeyStore trustStore = getKeyStore(filePath, keyStorePassword);
    TrustManagerFactory trustFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    trustFactory.init(trustStore);
    return trustFactory.getTrustManagers();
  }

  private static KeyStore getKeyStore(String filePath, String keyStorePassword) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
    KeyStore trustStore = KeyStore.getInstance("JKS");
    try (InputStream trustStoreIS = new FileInputStream(filePath)) {
      trustStore.load(trustStoreIS, keyStorePassword.toCharArray());
    }
    return trustStore;
  }

  private static ByteBuffer enlargeBuffer(ByteBuffer buffer, int sessionProposedCapacity) {
    if (sessionProposedCapacity > buffer.capacity()) {
      buffer = ByteBuffer.allocate(sessionProposedCapacity);
    } else {
      buffer = ByteBuffer.allocate(buffer.capacity() * 2);
    }
    return buffer;
  }

  private static ByteBuffer handleBufferUnderflow(SSLSession session, ByteBuffer buffer) {
    if (session.getPacketBufferSize() < buffer.limit()) {
      return buffer;
    } else {
      ByteBuffer replaceBuffer = enlargeBuffer(buffer, session.getPacketBufferSize());
      buffer.flip();
      replaceBuffer.put(buffer);
      return replaceBuffer;
    }
  }

  private void handleEndOfStream() throws IOException, ExecutionException, InterruptedException {
    try {
      engine.closeInbound();
    } catch (Exception e) {
      System.out.println("This engine was forced to close inbound, without having received the proper SSL/TLS close notification message from the peer, due to end of stream.");
    }
    close();
  }

  private void doHandShake() throws SSLException, ExecutionException, InterruptedException {
    System.out.println("About to do handshake...");
    engine.beginHandshake();

    SSLEngineResult result;
    SSLEngineResult.HandshakeStatus handshakeStatus;

    int appBufferSize = engine.getSession().getApplicationBufferSize();
    ByteBuffer myAppData = ByteBuffer.allocate(appBufferSize);
    ByteBuffer peerAppData = ByteBuffer.allocate(appBufferSize);
    netData.clear();
    peerNetData.clear();

    handshakeStatus = engine.getHandshakeStatus();
    while (handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED && handshakeStatus != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
      switch (handshakeStatus) {
        case NEED_UNWRAP:
          if (channel.read(peerNetData).get() < 0) {
            if (engine.isInboundDone() && engine.isOutboundDone()) {
              return;
            }
            try {
              engine.closeInbound();
            } catch (SSLException e) {
              System.out.println("This engine was forced to close inbound, without having received the proper SSL/TLS close notification message from the peer, due to end of stream.");
            }
            engine.closeOutbound();
            // After closeOutbound the engine will be set to WRAP state, in order to try to send a close message to the client.
            handshakeStatus = engine.getHandshakeStatus();
            break;
          }
          peerNetData.flip();
          try {
            result = engine.unwrap(peerNetData, peerAppData);
            peerNetData.compact();
            handshakeStatus = result.getHandshakeStatus();
          } catch (SSLException sslException) {
            System.out.println("A problem was encountered while processing the data that caused the SSLEngine to abort. Will try to properly close connection...");
            engine.closeOutbound();
            handshakeStatus = engine.getHandshakeStatus();
            break;
          }
          switch (result.getStatus()) {
            case OK:
              break;
            case BUFFER_OVERFLOW:
              // Will occur when peerAppData's capacity is smaller than the data derived from peerNetData's unwrap.
              peerAppData = enlargeBuffer(peerAppData, session.getApplicationBufferSize());
              break;
            case BUFFER_UNDERFLOW:
              // Will occur either when no data was read from the peer or when the peerNetData buffer was too small to hold all peer's data.
              peerNetData = handleBufferUnderflow(session, peerNetData);
              break;
            case CLOSED:
              if (engine.isOutboundDone()) {
                return;
              } else {
                engine.closeOutbound();
                handshakeStatus = engine.getHandshakeStatus();
                break;
              }
            default:
              throw new IllegalStateException("Invalid SSL status: " + result.getStatus());
          }
          break;
        case NEED_WRAP:
          netData.clear();
          try {
            result = engine.wrap(myAppData, netData);
            handshakeStatus = result.getHandshakeStatus();
          } catch (SSLException sslException) {
            System.out.println("A problem was encountered while processing the data that caused the SSLEngine to abort. Will try to properly close connection...");
            engine.closeOutbound();
            handshakeStatus = engine.getHandshakeStatus();
            break;
          }
          switch (result.getStatus()) {
            case OK :
              netData.flip();
              while (netData.hasRemaining()) {
                channel.write(netData);
              }
              break;
            case BUFFER_OVERFLOW:
              // Will occur if there is not enough space in myNetData buffer to write all the data that would be generated by the method wrap.
              // Since myNetData is set to session's packet size we should not get to this point because SSLEngine is supposed
              // to produce messages smaller or equal to that, but a general handling would be the following:
              netData = enlargeBuffer(netData, session.getPacketBufferSize());
              break;
            case BUFFER_UNDERFLOW:
              throw new SSLException("Buffer underflow occurred after a wrap. I don't think we should ever get here.");
            case CLOSED:
              try {
                netData.flip();
                while (netData.hasRemaining()) {
                  channel.write(netData);
                }
                // At this point the handshake status will probably be NEED_UNWRAP so we make sure that peerNetData is clear to read.
                peerNetData.clear();
              } catch (Exception e) {
                System.out.println("Failed to send server's CLOSE message due to socket channel's failure.");
                handshakeStatus = engine.getHandshakeStatus();
              }
              break;
            default:
              throw new IllegalStateException("Invalid SSL status: " + result.getStatus());
          }
          break;
        case NEED_TASK:
          Runnable task;
          while ((task = engine.getDelegatedTask()) != null) {
            executor.execute(task);
          }
          handshakeStatus = engine.getHandshakeStatus();
          break;
        default:
          throw new IllegalStateException("Invalid SSL status: " + handshakeStatus);
      }
    }
  }
}
