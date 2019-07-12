package net.neuralm.client.messages.serializer;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;

public class JsonSerializer implements ISerializer {

    final Gson gson;

    public JsonSerializer() {
        this.gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
    }

    @Override
    public byte[] serialize(Object message) {
        String json = gson.toJson(message);

        return json.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Object deserialize(byte[] bytes, String typeName) {
        Class clazz;
        try {
            clazz = Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        String json = new String(bytes, StandardCharsets.UTF_8);

        return gson.fromJson(json, clazz);
    }
}
