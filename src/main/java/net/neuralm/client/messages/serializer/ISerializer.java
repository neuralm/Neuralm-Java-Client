package net.neuralm.client.messages.serializer;

public interface ISerializer {

    /***
     * Serializes the given object into a byte array.
     * Deserializing the byte array using {@link ISerializer#deserialize(byte[], Class)} should result in an object equal to the original object.
     * @param message The message to be serialized
     * @return The byte array representing the message.
     */
    byte[] serialize(Object message);

    /***
     * Serializes the given byte array into the given type.
     * The object returned should equal the original object passed into {@link ISerializer#serialize(Object)}
     * @param bytes The byte array representing the serialized state of the object
     * @param type The class of the object that was serialized
     * @param <T> The type of the object that was serialized to
     * @return The deserialized object
     */
    <T> T deserialize(byte[] bytes, Class<T> type);

}
