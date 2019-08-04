package net.neuralm.client;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ConnectionHandler implements CompletionHandler<Void, NeuralmClient> {

    private final AsynchronousSSLChannel channel;

    ConnectionHandler(AsynchronousSSLChannel channel) {
        this.channel = channel;
    }

    @Override
    public void completed(Void result, NeuralmClient client) {
        try {
            System.out.println(String.format("Connected %s!", channel.getRemoteAddress()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        client.fireEvent("Connected", client);

        client.startReading();
    }

    @Override
    public void failed(Throwable exc, NeuralmClient client) {
        System.err.println("Failed to connect!");
        exc.printStackTrace();

        client.fireEvent("ConnectingFailed", client);

        if (client.autoReconnect) {
            try {
                Thread.sleep(client.autoReconnectWaitTime);
                client.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
