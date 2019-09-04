package net.neuralm.client.neat;

import net.neuralm.client.entities.User;

import java.util.UUID;

public class TrainingRoom {

    public UUID id;
    public String name;
    public User owner;
    public int generation;
    public TrainingRoomSettings trainingRoomSettings;

}
