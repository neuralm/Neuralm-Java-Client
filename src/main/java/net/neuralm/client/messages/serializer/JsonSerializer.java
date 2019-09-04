package net.neuralm.client.messages.serializer;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.nio.charset.StandardCharsets;

public class JsonSerializer implements ISerializer {

    private final Gson gson;

    public JsonSerializer() {
        this.gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] serialize(Object message) {
        String json = gson.toJson(message);

        return json.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * {@inheritDoc}
     *
     * @return The deserialized object, or null if a {@link JsonSyntaxException} occurred.
     */
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) {
        String json = new String(bytes, StandardCharsets.UTF_8);

        try {
            return gson.fromJson(json, type);
        } catch (JsonSyntaxException exception) {
            System.err.println(json);
            exception.printStackTrace();
            return null;
        }
    }
}
