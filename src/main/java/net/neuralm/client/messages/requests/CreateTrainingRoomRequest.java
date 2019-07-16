package net.neuralm.client.messages.requests;

import java.util.UUID;
import net.neuralm.client.neat.TrainingRoomSettings;

public class CreateTrainingRoomRequest extends Request {

    public final UUID ownerId;
    public final String trainingRoomName;
    public final TrainingRoomSettings trainingRoomSettings;

    public CreateTrainingRoomRequest(UUID ownerId, String trainingRoomName, TrainingRoomSettings trainingRoomSettings) {
        this.ownerId = ownerId;
        this.trainingRoomName = trainingRoomName;
        this.trainingRoomSettings = trainingRoomSettings;
    }
}
