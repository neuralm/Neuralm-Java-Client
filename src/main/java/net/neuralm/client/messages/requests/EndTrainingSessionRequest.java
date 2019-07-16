package net.neuralm.client.messages.requests;

import java.util.UUID;

public class EndTrainingSessionRequest extends Request {

    public final UUID trainingSessionId;

    public EndTrainingSessionRequest(UUID trainingSessionId) {
        this.trainingSessionId = trainingSessionId;
    }
}
