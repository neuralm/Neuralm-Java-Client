package net.neuralm.client.messages.requests;

import java.util.UUID;

public class StartTrainingSessionRequest extends Request {

    public final UUID userId;
    public final UUID trainingRoomId;

    public StartTrainingSessionRequest(UUID userId, UUID trainingRoomId) {
        this.userId = userId;
        this.trainingRoomId = trainingRoomId;
    }
}
