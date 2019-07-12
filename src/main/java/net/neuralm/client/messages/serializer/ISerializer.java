package net.neuralm.client.messages.serializer;

public interface ISerializer {

    byte[] serialize(Object message);

    <T> T deserialize(byte[] bytes, String typeName);

}
