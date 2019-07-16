package net.neuralm.client;

import java.nio.channels.CompletionHandler;
import net.neuralm.client.messages.Message;

public class WriteHandler implements CompletionHandler<Integer, Message> {

    private final NeuralmClient client;

    WriteHandler(NeuralmClient client) {
        this.client = client;
    }

    @Override
    public void completed(Integer result, Message attachment) {
        if (client.writeBufferHasRemaining()) {
            client.forceWriteBufferToCompletion();
            System.out.println("Forced write buffer to be clear");
        }
        if (client.hasMessagesInQueue()) {
            Message message = client.getMessageFromQueue();
            client.hardSend(message);
        } else {
            client.isWriting.set(false);
        }
    }

    @Override
    public void failed(Throwable exc, Message attachment) {
        exc.printStackTrace();
    }
}