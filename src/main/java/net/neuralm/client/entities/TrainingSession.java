package net.neuralm.client.entities;

import java.util.UUID;
import net.neuralm.client.neat.TrainingRoom;

public class TrainingSession {

    public UUID Id;
    public String StartedTimestamp;
    public String EndedTimestamp; //TODO: Make this an actual date object if possible, or maybe unix time
    public UUID UserId;
    public TrainingRoom trainingRoom;

}
