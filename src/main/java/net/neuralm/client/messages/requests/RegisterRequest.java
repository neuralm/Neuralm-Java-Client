package net.neuralm.client.messages.requests;

public class RegisterRequest extends Request {

    public final String username;
    public final String password;
    public final String credentialTypeCode;

    public RegisterRequest(String username, String password, String credentialTypeCode) {
        super();
        this.username = username;
        this.password = password;
        this.credentialTypeCode = credentialTypeCode;
    }
}
