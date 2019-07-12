package net.neuralm.client;

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

public class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {

    private final NeuralmClient client;

    public ReadHandler(NeuralmClient client) {
        this.client = client;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        client.startReading();

        attachment.flip();
        byte[] bytes = new byte[attachment.remaining()];
        attachment.get(bytes);

        String message = new String(bytes, StandardCharsets.UTF_8);
//        System.out.println(String.format("Got message: %s", message));
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        exc.printStackTrace();
    }
}
