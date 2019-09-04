package net.neuralm.client.messages;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class MessageHeader {

    private final int bodySize;
    private final String typeName;

    private MessageHeader(int bodySize, String typeName) {
        this.bodySize = bodySize;
        this.typeName = typeName;
    }

    /***
     * Parse a byte array as a header.
     * @param header The byte array containing the data for the header
     * @return The header if parsed, or null if failed
     */
    public static MessageHeader parseHeader(byte[] header) {
        if (header.length < 9) {
            System.err.println(String.format("Invalid header received! Length was %s but expected at least 9", header.length));
            return null;
        }

        //Wrap bytes into buffer
        ByteBuffer buffer = ByteBuffer.wrap(header).order(ByteOrder.LITTLE_ENDIAN);

        //First 4 bytes is the complete header size
        int headerSize = buffer.getInt(0);

        //Second 4 bytes is the size of the body
        int bodySize = buffer.getInt(4);

        //The typeName is everything left, so headerSize - the 8 bytes for the first 2 integers
        byte[] typeNameBytes = new byte[headerSize - 8];
        buffer.position(8);
        buffer.get(typeNameBytes, 0, headerSize - 8);

        String typeName = new String(typeNameBytes, StandardCharsets.UTF_8);

        return new MessageHeader(bodySize, typeName);
    }

    public String getTypeName() {
        return typeName;
    }

    public int getBodySize() {
        return bodySize;
    }
}
