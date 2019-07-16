package net.neuralm.client.messages.responses;

import java.util.UUID;

public class AuthenticateResponse extends Response {

    private String accessToken;
    private UUID userId;

    public String getAccessToken() {
        return accessToken;
    }

    public UUID getUserId() {
        return userId;
    }

}
