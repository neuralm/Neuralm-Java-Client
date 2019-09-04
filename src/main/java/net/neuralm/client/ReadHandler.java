package net.neuralm.client;

import net.neuralm.client.messages.Message;
import net.neuralm.client.messages.MessageHeader;
import net.neuralm.client.messages.responses.Response;
import net.neuralm.client.messages.serializer.ISerializer;

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;

public class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {

    private final NeuralmClient client;

    MessageHeader currentHeader;
    final ISerializer serializer;

    ByteBuffer currentBody;

    public ReadHandler(NeuralmClient client, ISerializer serializer) {
        this.client = client;
        this.serializer = serializer;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {

        attachment.flip();
        byte[] bytes = new byte[attachment.remaining()];
        attachment.get(bytes);

        if (currentHeader == null) {
            currentHeader = MessageHeader.parseHeader(bytes);

            if (currentHeader == null) {
                System.err.println("Received message that should be header but isn't");
                client.startReading();
                return;
            }

            client.startReading(currentHeader.getBodySize());
            currentBody = ByteBuffer.allocate(currentHeader.getBodySize());
        } else {
            currentBody.put(bytes);

            client.startReading();

            if (currentBody.position() == currentBody.capacity()) {
                Object response = Message.deconstructMessageBody(serializer, currentHeader, currentBody.array());

                if (response instanceof Response) {
                    client.addResponse((Response) response);
                } else {
                    System.err.println(String.format("Received response that wasn't a response! Bytes: %s", currentBody.array()));
                }

                currentHeader = null;
            }
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        exc.printStackTrace();
    }
}
