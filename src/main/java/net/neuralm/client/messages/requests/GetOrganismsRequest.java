package net.neuralm.client.messages.requests;

import java.util.UUID;

public class GetOrganismsRequest extends Request {

    public final UUID trainingSessionID;

    public final int amount;

    public GetOrganismsRequest(UUID trainingSessionID, int amount) {
        this.trainingSessionID = trainingSessionID;
        this.amount = amount;
    }
}
