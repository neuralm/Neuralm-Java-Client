package net.neuralm.client.entities;

import net.neuralm.client.neat.TrainingRoom;

import java.util.UUID;

public class TrainingSession {

    public UUID id;
    public String startedTimestamp;
    public String endedTimestamp; //TODO: Make this an actual date object if possible, or maybe unix time
    public UUID userId;
    public TrainingRoom trainingRoom;

}
