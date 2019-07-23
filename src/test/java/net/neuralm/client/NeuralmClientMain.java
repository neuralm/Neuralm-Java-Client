package net.neuralm.client;

import net.neuralm.client.messages.requests.*;
import net.neuralm.client.messages.responses.AuthenticateResponse;
import net.neuralm.client.messages.responses.GetEnabledTrainingRoomsResponse;
import net.neuralm.client.messages.responses.Response;
import net.neuralm.client.messages.serializer.JsonSerializer;
import net.neuralm.client.neat.TrainingRoom;
import net.neuralm.client.neat.TrainingRoomSettings;

import java.io.IOException;

public class NeuralmClientMain {
    public static void main(String... args) throws IOException, InterruptedException {
        String host = args.length >= 1 ? args[0] : "127.0.0.1";
        int port = args.length >= 2 ? Integer.parseInt(args[1]) : 9999;

        NeuralmClient client = new NeuralmClient(host, port, new JsonSerializer(), true, 5 * 1000);

        client.addListener("RegisterResponse", (changeEvent) -> {
            Response response = (Response) changeEvent.getNewValue();
            System.out.println(response.isSuccess() ? "Registered..." : String.format("Registering failed: %s", response.getMessage()));

            Request request = new AuthenticateRequest("suppergerrie2", "hi", "Name");
            client.send(request);
        });

        client.addListener("AuthenticateResponse", (changeEvent) -> {
            AuthenticateResponse response = (AuthenticateResponse) changeEvent.getNewValue();

            System.out.println(response.isSuccess() ? "Authenticated..." : String.format("Authenticating failed: %s", response.getMessage()));

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
