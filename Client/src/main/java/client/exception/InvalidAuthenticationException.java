package client.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class InvalidAuthenticationException extends Exception {
    private String field;

    public InvalidAuthenticationException(String message, Throwable cause) {
        super(message,cause);
    }

    @JsonCreator
    public InvalidAuthenticationException(@JsonProperty("message") String message,@JsonProperty("field") String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public static InvalidAuthenticationException getHttpException(String errorMessage) {
        System.out.println(errorMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(errorMessage, InvalidAuthenticationException.class);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }
}
