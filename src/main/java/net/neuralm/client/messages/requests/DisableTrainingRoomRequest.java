package net.neuralm.client.messages.requests;

public class DisableTrainingRoomRequest extends Request {

    public final int trainingRoomId;
    public final int userId;

    public DisableTrainingRoomRequest(int trainingRoomId, int userId) {
        this.trainingRoomId = trainingRoomId;
        this.userId = userId;
    }
}
