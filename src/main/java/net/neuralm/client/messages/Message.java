package net.neuralm.client.messages;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import net.neuralm.client.messages.serializer.ISerializer;

public class Message {

    private final byte[] headerBytes;
    private final byte[] bodyBytes;

    private Message(byte[] headerBytes, byte[] bodyBytes) {
        this.headerBytes = headerBytes;
        this.bodyBytes = bodyBytes;
    }

    public byte[] getHeaderBytes() {
        return headerBytes;
    }

    public byte[] getBodyBytes() {
        return bodyBytes;
    }

    public static Message constructMessage(ISerializer serializer, Object message) {
        byte[] bodyBytes = serializer.serialize(message);
        byte[] messageTypeBytes = message.getClass().getSimpleName().getBytes(StandardCharsets.UTF_8);

        int headerSize = 4+4+messageTypeBytes.length;
        byte[] headerBytes = ByteBuffer.allocate(headerSize).order(ByteOrder.LITTLE_ENDIAN).putInt(headerSize).putInt(bodyBytes.length).put(messageTypeBytes).array();

        return new Message(headerBytes, bodyBytes);
    }

    public int getTotalSize() {
        return headerBytes.length + bodyBytes.length;
    }

    public Object deconstructMessageBody(ISerializer serializer) {
        MessageHeader header = MessageHeader.parseHeader(headerBytes);
        if (header == null)
            return null;
        return serializer.deserialize(bodyBytes, header.getTypeName());
    }
}
