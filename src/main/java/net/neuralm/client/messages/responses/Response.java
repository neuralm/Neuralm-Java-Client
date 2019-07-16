package net.neuralm.client.messages.responses;

import java.util.UUID;

public class Response {

    private UUID id;
    private UUID requestId;
    private String dateTime;
    private String message;
    private boolean success;

    /***
     * Get the id of this response.
     * @return The id of this response
     */
    public UUID getId() {
        return id;
    }

    /***
     * Get the id of the request which this is a response to.
     * @return The request's id.
     */
    public UUID getRequestId() {
        return requestId;
    }

    /***
     * The time date this response was send.
     * @return The date as a string in the following format: "yyyy-MM-dd'T'HH:mm:ss'.'SSSSSSS'Z'"
     */
    public String getDateTime() {
        return dateTime;
    }

    /***
     * A message from the server, this can be empty and often contains the reason why the request failed.
     * {@see isSuccess}
     * @return A message from the server as a string
     */
    public String getMessage() {
        return message;
    }

    /***
     * Whether the request was a success, if it failed check {@link Response#getMessage} for the reason.
     * @return True if the request was successful, else false.
     */
    public boolean isSuccess() {
        return success;
    }
}
