package net.neuralm.client;

import java.io.IOException;
import net.neuralm.client.messages.requests.AuthenticateRequest;
import net.neuralm.client.messages.requests.CreateTrainingRoomRequest;
import net.neuralm.client.messages.requests.GetEnabledTrainingRoomsRequest;
import net.neuralm.client.messages.requests.RegisterRequest;
import net.neuralm.client.messages.requests.Request;
import net.neuralm.client.messages.responses.AuthenticateResponse;
import net.neuralm.client.messages.responses.GetEnabledTrainingRoomsResponse;
import net.neuralm.client.messages.responses.Response;
import net.neuralm.client.messages.serializer.JsonSerializer;
import net.neuralm.client.neat.TrainingRoom;
import net.neuralm.client.neat.TrainingRoomSettings;
import org.junit.jupiter.api.Assertions;

public class NeuralmClientMain {
    public static void main(String... args) throws IOException, InterruptedException {
        NeuralmClient client = new NeuralmClient("127.0.0.1", 25568, new JsonSerializer(), true, 5 * 1000);

        client.addListener("RegisterResponse", (changeEvent) -> {
            Request request = new AuthenticateRequest("suppergerrie2", "hi", "Name");
            client.send(request);
        });

        client.addListener("AuthenticateResponse", (changeEvent) -> {
            AuthenticateResponse response = (AuthenticateResponse) changeEvent.getNewValue();

            Assertions.assertTrue(response.isSuccess(), "Couldn't authenticate");
// 384cc1f1-2506-45b6-72c7-08d709ce803c
            client.send(new CreateTrainingRoomRequest(response.getUserId(), "supperroom", new TrainingRoomSettings()));
        });

        client.addListener("CreateTrainingRoomResponse", (changeEvent) -> {
            Response response = (Response) changeEvent.getNewValue();
            System.out.println(String.format("%s trainingroom", response.isSuccess() ? "Created" : "Failed to create"));
            if (!response.isSuccess()) {
                System.out.println(response.getMessage());
            }
            client.send(new GetEnabledTrainingRoomsRequest());
        });

        client.addListener("GetEnabledTrainingRoomsResponse", (changeEvent) -> {
            GetEnabledTrainingRoomsResponse response = (GetEnabledTrainingRoomsResponse) changeEvent.getNewValue();

            System.out.println(String.format("Got %s trainingrooms!", response.getTrainingRooms().size()));
            for (TrainingRoom trainingRoom : response.getTrainingRooms()) {
                System.out.println("    Name:  " + trainingRoom.name);
                System.out.println("    Owner: " + trainingRoom.owner.username);
            }
        });

        client.addListener("Connected", evt -> {
            Request request = new RegisterRequest("suppergerrie2", "hi", "Name");

            client.send(request);
        });

        client.start();

        Thread.sleep(1000000);
    }
}
