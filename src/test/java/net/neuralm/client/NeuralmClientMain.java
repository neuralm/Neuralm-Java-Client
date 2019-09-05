package net.neuralm.client;

import net.neuralm.client.entities.TrainingSession;
import net.neuralm.client.messages.requests.*;
import net.neuralm.client.messages.responses.*;
import net.neuralm.client.messages.serializer.JsonSerializer;
import net.neuralm.client.neat.Organism;
import net.neuralm.client.neat.TrainingRoom;
import net.neuralm.client.neat.TrainingRoomSettings;

import java.io.IOException;
import java.util.UUID;

public class NeuralmClientMain {

    private static UUID userId;
    private static TrainingSession trainingSession;
    private static int organismBatchesProcessed;

    public static void main(String... args) throws IOException, InterruptedException {
        String host = args.length >= 1 ? args[0] : "127.0.0.1";
        int port = args.length >= 2 ? Integer.parseInt(args[1]) : 9999;

        NeuralmClient client = new NeuralmClient(host, port, new JsonSerializer(), true, 5 * 1000);
        String trainingRoomName = "java_client_test_" + UUID.randomUUID().toString();
        String username = "java_client_test_" + UUID.randomUUID();

        client.addListener("RegisterResponse", (changeEvent) -> {
            Response response = (Response) changeEvent.getNewValue();
            System.out.println(response.isSuccess() ? "Registered..." : String.format("Registering failed: %s", response.getMessage()));

            Request request = new AuthenticateRequest(username, "hi", "Name");
            client.send(request);
        });

        client.addListener("AuthenticateResponse", (changeEvent) -> {
            AuthenticateResponse response = (AuthenticateResponse) changeEvent.getNewValue();

            System.out.println(response.isSuccess() ? "Authenticated..." : String.format("Authenticating failed: %s", response.getMessage()));
            userId = response.getUserId();
            System.out.println(String.format("Creating room with name %s", trainingRoomName));
            client.send(new CreateTrainingRoomRequest(response.getUserId(), trainingRoomName, new TrainingRoomSettings().setOrganismCount(20).setInputCount(3).setOutputCount(1).setSeed(10).setAddConnectionChance(1).setAddNodeChance(1
            )));
        });

        client.addListener("CreateTrainingRoomResponse", (changeEvent) -> {
            CreateTrainingRoomResponse response = (CreateTrainingRoomResponse) changeEvent.getNewValue();
            System.out.println(String.format("%s trainingroom", response.isSuccess() ? "Created" : "Failed to create"));
            if (!response.isSuccess()) {
                System.out.println(response.getMessage());
            }
            client.send(new GetEnabledTrainingRoomsRequest());
        });

        client.addListener("GetEnabledTrainingRoomsResponse", (changeEvent) -> {
            GetEnabledTrainingRoomsResponse response = (GetEnabledTrainingRoomsResponse) changeEvent.getNewValue();
            System.out.println(String.format("GetEnabledTrainingRoomsResponse was %s", response.isSuccess() ? "successful" : "unsuccessful"));

            System.out.println(String.format("Got %s trainingrooms!", response.getTrainingRooms().size()));
            for (TrainingRoom trainingRoom : response.getTrainingRooms()) {

                if (trainingRoom.name.equals(trainingRoomName) && trainingRoom.owner.username.equals(username)) {
                    Request startTrainingSessionRequest = new StartTrainingSessionRequest(userId, trainingRoom.id);
                    client.send(startTrainingSessionRequest);
                }

                System.out.println("    Name:  " + trainingRoom.name);
                System.out.println("    Owner: " + trainingRoom.owner.username);
            }

        });

        client.addListener("StartTrainingSessionResponse", (changeEvent) -> {
            StartTrainingSessionResponse response = (StartTrainingSessionResponse) changeEvent.getNewValue();

            if (response.isSuccess()) {
                trainingSession = response.getTrainingSession();
                Request getOrganismRequest = new GetOrganismsRequest(trainingSession.id, 10);
                client.send(getOrganismRequest);
            } else {
                System.err.println(String.format("StartTrainingSessionResponse failed %s", response.getMessage()));
            }
        });

        client.addListener("GetOrganismsResponse", (changeEvent) -> {
            GetOrganismsResponse response = (GetOrganismsResponse) changeEvent.getNewValue();

            if (response.isSuccess()) {
                System.out.println(String.format("Got %s organism", response.getOrganisms().size()));

                Random random = new Random();
                for (Organism organism : response.getOrganisms()) {
                    organism.setScore(random.nextDouble() + 0.00001);
                    organism.initialize();
//                    System.out.println(organism.name + " has output: " + Arrays.toString(organism.getValue(new double[]{0, 1, 2})));
                }

                Request postOrganismsScoreRequest = new PostOrganismsScoreRequest(trainingSession.id, response.getOrganisms());
                client.send(postOrganismsScoreRequest);
            } else {
                System.err.println(String.format("GetOrganismsResponse failed %s", response.getMessage()));
            }
        });

        client.addListener("PostOrganismsScoreResponse", (changeEvent) -> {
            PostOrganismsScoreResponse response = (PostOrganismsScoreResponse) changeEvent.getNewValue();
            System.out.println(String.format("PostOrganismsScoreResponse was %s", response.isSuccess() ? "successful" : "unsuccessful"));

            organismBatchesProcessed++;
            if (organismBatchesProcessed == 3) {
                Request request = new EndTrainingSessionRequest(trainingSession.id);
                client.send(request);
            } else {
                Request getOrganismRequest = new GetOrganismsRequest(trainingSession.id, 10);
                client.send(getOrganismRequest);
            }
        });

        client.addListener("EndTrainingSessionResponse", (changeEvent) -> {
            Response response = (Response) changeEvent.getNewValue();
            System.out.println(String.format("EndTrainingSessionResponse was %s", response.isSuccess() ? "successful" : "unsuccessful"));

            System.exit(0);
        });

        client.addListener("Connected", evt -> {
            Request request = new RegisterRequest(username, "hi", "Name");

            client.send(request);
        });

        client.start();

        Thread.sleep(1000000);
    }
}
