package net.neuralm.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import net.neuralm.client.messages.Message;
import net.neuralm.client.messages.requests.Request;
import net.neuralm.client.messages.serializer.ISerializer;

public class NeuralmClient {

    private final String host;
    private final int port;
    final boolean autoReconnect;
    final long autoReconnectWaitTime;
    final ISerializer serializer;

    AsynchronousSocketChannel channel;

    private Queue<Message> sendQueue = new LinkedBlockingDeque<>();

    boolean isWriting;

    /**
     * Create a new NeuralmClient to communicate with the NeuralmServer
     * @param host The neuralm server host
     * @param port The port the neuralm server is running on
     * @param autoReconnect Whether to automatically reconnect when the connection attempt fails
     * @param autoReconnectWaitTime How long to wait after a connect fails before reconnecting
     * @throws IOException
     */
    public NeuralmClient(String host, int port, ISerializer serializer, boolean autoReconnect, long autoReconnectWaitTime) throws IOException {
        this.host = host;
        this.port = port;
        this.autoReconnect = autoReconnect;
        this.autoReconnectWaitTime = autoReconnectWaitTime;
        this.serializer = serializer;

        connect();
    }

    public NeuralmClient(String host, int port, ISerializer serializer) throws IOException {
        this(host, port, serializer, false, -1);
    }

    void connect() throws IOException {
        System.out.println(String.format("Attempting to connect to %s::%s", host, port));
        channel = AsynchronousSocketChannel.open();
        channel.connect(new InetSocketAddress(host, port), this, new ConnectionHandler());
    }

    void startReading() {
        ByteBuffer buffer = ByteBuffer.allocate(1024).order(ByteOrder.LITTLE_ENDIAN);
        channel.read(buffer, buffer, new ReadHandler(this));
    }

    public void send(Request request) {
        send(Message.constructMessage(serializer, request));
    }

    public void send(Message message) {
//        if(isWriting) {
//            sendQueue.add(message);
//            return;
//        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(message.getHeaderByes().length + message.getBodyBytes().length).order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(message.getHeaderByes());
        byteBuffer.put(message.getBodyBytes());
        byteBuffer.flip();

        try {
            channel.write(byteBuffer/*, message, new CompletionHandler<Integer, Message>() {
                @Override
                public void completed(Integer result, Message attachment) {
                    System.out.println(String.format("Send %s", message));
                    isWriting = false;
                    if(sendQueue.size()>0) {
                        send(sendQueue.poll());
                    }
                }

                @Override
                public void failed(Throwable exc, Message attachment) {
                    isWriting = false;

                    System.err.println(String.format("Failed to send %s", message));
                    exc.printStackTrace();
                }
            }*/).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
//        isWriting = true;
    }
}
