package net.neuralm.client.messages.responses;

import net.neuralm.client.entities.TrainingSession;

public class StartTrainingSessionResponse extends Response {

    private TrainingSession trainingSession;

    public TrainingSession getTrainingSession() {
        return trainingSession;
    }
}
