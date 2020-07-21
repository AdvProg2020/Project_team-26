package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class InvalidAuthenticationException extends Exception {
    private String field;

    public InvalidAuthenticationException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getFieldName() {
        return field;
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
