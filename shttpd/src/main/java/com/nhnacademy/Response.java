package com.nhnacademy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Response {
    static final String CRLF = "\r\n";
    String version;
    String status;
    String reason;
    String contentLength;
    StringBuilder statusBuilder;
    StringBuilder headerBuilder;
    StringBuilder bodyBuilder;

    public Response(String version, String status, String reason) {
        this.version = version;
        this.status = status;
        this.reason = reason;
        statusBuilder = new StringBuilder();
        headerBuilder = new StringBuilder();
        bodyBuilder = new StringBuilder();

        setStatusLine();
        setHeader();
    }

    public void setStatusLine() {
        statusBuilder.append(String.format("%s %s %s%s", version, status, reason, CRLF));
    }

    public void setHeader() {
        headerBuilder.append(String.format("Date: %s%s", now(), CRLF));
        headerBuilder.append(String.format("Content-Type: text/html%s", CRLF));
        headerBuilder.append(String.format("Content-Length: %s", CRLF));
        headerBuilder.append(String.format("Connection: keep-avlie%s", CRLF));
        headerBuilder.append(String.format("Server: shttpd/1.0.0 %s%s", CRLF, CRLF));
    }

    public void setBody(String line) {
        bodyBuilder.append(String.format("%s%s",line, CRLF));
    }

    public String getMessage() {
        StringBuilder messageBuilder = new StringBuilder();
        
        messageBuilder.append(statusBuilder);
        messageBuilder.append(headerBuilder);
        messageBuilder.append(bodyBuilder);

        contentLength = String.format("%s", messageBuilder.toString().length() + String.format("%d", messageBuilder.toString().length()).length());

        int contentLengthIndex = messageBuilder.indexOf("Content-Length: ");
        messageBuilder.replace(contentLengthIndex, contentLengthIndex + 15, String.format("Content-Length: %s", contentLength));

        return messageBuilder.toString();
    }

    public String now() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }
}
