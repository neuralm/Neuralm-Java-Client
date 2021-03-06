package net.neuralm.client.messages.requests;

public class AuthenticateRequest extends Request {

    public final String username;

    public final String password;

    public final String credentialTypeCode;

    public AuthenticateRequest(String username, String password, String credentialTypeCode) {
        super();
        this.username = username;
        this.password = password;
        this.credentialTypeCode = credentialTypeCode;
    }
}
