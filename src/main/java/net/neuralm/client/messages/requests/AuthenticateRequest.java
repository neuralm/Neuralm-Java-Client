package net.neuralm.client.messages.requests;

import com.google.gson.annotations.SerializedName;

public class AuthenticateRequest extends Request {

    String username;

    String password;

    String credentialTypeCode;

    public AuthenticateRequest(String username, String password, String credentialTypeCode) {
        this.username = username;
        this.password = password;
        this.credentialTypeCode = credentialTypeCode;
    }
}
