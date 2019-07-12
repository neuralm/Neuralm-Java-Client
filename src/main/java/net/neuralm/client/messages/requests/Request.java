package net.neuralm.client.messages.requests;

import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class Request {

    UUID id;

    String date;

    public Request() {
        id = UUID.randomUUID();
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'.'SSSSSSS'Z'"));
    }

}
