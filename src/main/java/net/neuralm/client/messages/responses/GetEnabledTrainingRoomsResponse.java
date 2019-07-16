package net.neuralm.client.messages.responses;

import java.util.Collections;
import java.util.List;
import net.neuralm.client.neat.TrainingRoom;

public class GetEnabledTrainingRoomsResponse extends Response {

    private List<TrainingRoom> trainingRooms;

    /***
     * Get the enabled trainingrooms received from the server.
     * @return An unmodifiable list containing all enabled training rooms.
     */
    public List<TrainingRoom> getTrainingRooms() {
        return Collections.unmodifiableList(trainingRooms);
    }
}
