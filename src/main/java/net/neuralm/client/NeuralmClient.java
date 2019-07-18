package net.neuralm.client;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
import net.neuralm.client.messages.responses.Response;
import net.neuralm.client.messages.serializer.ISerializer;

public class NeuralmClient {

    final boolean autoReconnect;
    final long autoReconnectWaitTime;
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private final String host;
    private final int port;
    private final ISerializer serializer;
    private final ReadHandler readHandler;
    AtomicBoolean isWriting = new AtomicBoolean(false);
    private AsynchronousSocketChannel channel;
    private ByteBuffer writeBuffer = ByteBuffer.allocate(0);
    private Queue<Message> sendQueue = new LinkedBlockingDeque<>();


    /**
     * Create a new NeuralmClient to communicate with the NeuralmServer The connection is not started until {@link NeuralmClient#start()} is called. This so you can register listeners before the client starts
     *
     * @param host The neuralm server host
     * @param port The port the neuralm server is running on
     * @param autoReconnect Whether to automatically reconnect when the connection attempt fails
     * @param autoReconnectWaitTime How long to wait after a connect fails before reconnecting
     */
    public NeuralmClient(String host, int port, ISerializer serializer, boolean autoReconnect, long autoReconnectWaitTime) {
        this.host = host;
        this.port = port;
        this.autoReconnect = autoReconnect;
        this.autoReconnectWaitTime = autoReconnectWaitTime;
        this.serializer = serializer;
        this.readHandler = new ReadHandler(this, serializer);
    }

    public NeuralmClient(String host, int port, ISerializer serializer) throws IOException {
        this(host, port, serializer, false, -1);
    }

    /***
     * Start the client.
     * @throws IOException
     */
    public void start() throws IOException {
        connect();
    }

    void connect() throws IOException {
        System.out.println(String.format("Attempting to connect to %s::%s", host, port));
        channel = AsynchronousSocketChannel.open();
        channel.connect(new InetSocketAddress(host, port), this, new ConnectionHandler(channel));
    }

    void startReading() {
        startReading(1024);
    }

    void startReading(int size) {
        ByteBuffer buffer = ByteBuffer.allocate(size).order(ByteOrder.LITTLE_ENDIAN);
        channel.read(buffer, buffer, readHandler);
    }

    public void send(Request request) {
        Message message = Message.constructMessage(serializer, request);
        if (writeBuffer.hasRemaining() || isWriting.get()) {
            sendQueue.add(message);
            return;
        }
        isWriting.set(true);
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

    void addResponse(Response response) {
        propertyChangeSupport.firePropertyChange(response.getClass().getSimpleName(), null, response);
    }

    public void addListener(String eventName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(eventName, listener);
    }

    public void removeListener(String eventName, PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(eventName, listener);
    }

    void fireEvent(String eventName, NeuralmClient client) {
        propertyChangeSupport.firePropertyChange(eventName, null, client);
    }
}
