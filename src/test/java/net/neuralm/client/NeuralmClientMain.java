package net.neuralm.client;

import java.io.IOException;
import net.neuralm.client.messages.requests.AuthenticateRequest;
import net.neuralm.client.messages.requests.Request;
import net.neuralm.client.messages.serializer.JsonSerializer;

public class NeuralmClientMain {

    public static void main(String... args) throws IOException, InterruptedException {
        NeuralmClient client = new NeuralmClient("127.0.0.1", 9999, new JsonSerializer(), true, 5*1000);

        Thread.sleep(100);

        int i = 0;
        while(true) {
            Request request = new AuthenticateRequest(""+(i++), "no_u", "pls");
            client.send(request);
        }
    }

}
