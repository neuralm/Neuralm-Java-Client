package net.neuralm.client.neat;

import java.util.UUID;

public class Organism {

    public UUID id;

    public UUID speciesId;

    public UUID brainId;

    public UUID trainingRoomId;

    public Brain brain;

    public double score;

    public String name;

    public int generation;

    TrainingRoom trainingRoom;

    /**
     * Initialize the organism, this will set its trainingRoom and make sure the brain is ready to be used.
     *
     * @param trainingRoom The trainingroom this organism is part of.
     */
    public void initialize(TrainingRoom trainingRoom) {
        if (!trainingRoom.id.equals(trainingRoomId)) {
            throw new IllegalArgumentException(String.format("The given trainingRoomID %s does not match the expected trainingRoomID %s", trainingRoom.id, id));
        }

        this.trainingRoom = trainingRoom;
        this.brain.initialize(trainingRoom, this);
    }

    public double[] evaluate(double[] inputs) {
        return this.brain.evaluate(inputs);
    }
}
