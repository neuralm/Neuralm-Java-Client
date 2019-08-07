package net.neuralm.client.messages.requests;

import java.util.UUID;

public class GetOrganismsRequest extends Request {

    public UUID trainingSessionID;

    public int amount;

    public GetOrganismsRequest(UUID trainingSessionID, int amount) {
        this.trainingSessionID = trainingSessionID;
        this.amount = amount;
    }
}
