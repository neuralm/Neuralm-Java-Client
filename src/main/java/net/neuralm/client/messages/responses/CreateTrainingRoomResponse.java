package net.neuralm.client.messages.responses;

import java.util.UUID;

public class CreateTrainingRoomResponse extends Response {

    private UUID trainingRoomId;

    public UUID getTrainingRoomId() {
        return trainingRoomId;
    }
}
