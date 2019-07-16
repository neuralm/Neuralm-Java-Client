package net.neuralm.client.neat;

import java.util.UUID;
import net.neuralm.client.entities.User;

public class TrainingRoom {

    public UUID id;
    public String name;
    public User owner;
    public int generation;
    public TrainingRoomSettings trainingRoomSettings;
    public double highestScore;
    public double lowestScore;
    public double averageScore;

}
