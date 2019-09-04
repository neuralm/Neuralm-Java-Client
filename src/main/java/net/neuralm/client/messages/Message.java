package net.neuralm.client.messages;

import net.neuralm.client.messages.serializer.ISerializer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class Message {

    private final byte[] headerBytes;
    private final byte[] bodyBytes;

    private Message(byte[] headerBytes, byte[] bodyBytes) {
        this.headerBytes = headerBytes;
        this.bodyBytes = bodyBytes;
    }

    /**
     * Construct a message object from the given object with the given serializer.
     * It will be turned into bytes and a header will be created.
     * @param serializer The serializer to use.
     * @param message The object that should be turned into a message
     * @return The message with the headerBytes and bodyBytes.
     */
    public static Message constructMessage(ISerializer serializer, Object message) {
        byte[] bodyBytes = serializer.serialize(message);
        byte[] messageTypeBytes = message.getClass().getSimpleName().getBytes(StandardCharsets.UTF_8);

        int headerSize = 4 + 4 + messageTypeBytes.length;
        byte[] headerBytes = ByteBuffer.allocate(headerSize).order(ByteOrder.LITTLE_ENDIAN).putInt(headerSize).putInt(bodyBytes.length).put(messageTypeBytes).array();

        return new Message(headerBytes, bodyBytes);
    }

    /**
     * Turn a byte array representing an object into that object.
     * It will deserialize the bytes using the given serializer.
     * @param serializer The serializer to use.
     * @param header The header corresponding to the data being deserialized.
     * @param bodyBytes The data to deserialize
     * @return null if the serializer fails or no class corresponding to {@link MessageHeader#getTypeName()} is found.
     */
    public static Object deconstructMessageBody(ISerializer serializer, MessageHeader header, byte[] bodyBytes) {
        Class<?> clazz;
        try {
            clazz = Class.forName("net.neuralm.client.messages.responses." + header.getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return serializer.deserialize(bodyBytes, clazz);
    }

    public byte[] getHeaderBytes() {
        return headerBytes;
    }

    public byte[] getBodyBytes() {
        return bodyBytes;
    }

    public int getTotalSize() {
        return headerBytes.length + bodyBytes.length;
    }
}
