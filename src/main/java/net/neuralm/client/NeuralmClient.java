package net.neuralm.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

import net.neuralm.client.messages.Message;
import net.neuralm.client.messages.requests.Request;
import net.neuralm.client.messages.serializer.ISerializer;

public class NeuralmClient {

    private final String host;
    private final int port;
    final boolean autoReconnect;
    final long autoReconnectWaitTime;
    private final ISerializer serializer;

    private AsynchronousSocketChannel channel;
    private ByteBuffer writeBuffer = ByteBuffer.allocate(0);
    private Queue<Message> sendQueue = new LinkedBlockingDeque<>();
    AtomicBoolean isWriting = new AtomicBoolean(false);

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
        channel.connect(new InetSocketAddress(host, port), this, new ConnectionHandler(channel));
    }

    void startReading() {
        ByteBuffer buffer = ByteBuffer.allocate(1024).order(ByteOrder.LITTLE_ENDIAN);
        channel.read(buffer, buffer, new ReadHandler(this));
    }

    public void send(Request request) {
        Message message = Message.constructMessage(serializer, request);
        if (writeBuffer.hasRemaining() || isWriting.get()) {
            sendQueue.add(message);
            System.out.println("Put message in queue!");
            return;
        }
        isWriting.set(true);
        System.out.println("isWriting is set to true");
        hardSend(message);
    }

    void hardSend(Message message) {
        writeBuffer = ByteBuffer.allocate(message.getHeaderBytes().length + message.getBodyBytes().length).order(ByteOrder.LITTLE_ENDIAN);
        writeBuffer.put(message.getHeaderBytes());
        writeBuffer.put(message.getBodyBytes());
        writeBuffer.flip();
        channel.write(writeBuffer, message, new WriteHandler(this));
    }

    Boolean hasMessagesInQueue() {
        return !sendQueue.isEmpty();
    }

    Message getMessageFromQueue() {
        return sendQueue.poll();
    }

    void forceWriteBufferToCompletion() {
        writeBuffer.position(writeBuffer.limit());
    }

    boolean writeBufferHasRemaining() {
        return writeBuffer.hasRemaining();
    }
}
