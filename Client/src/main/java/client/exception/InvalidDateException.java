package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class InvalidDateException extends Exception {
    public InvalidDateException(String message) {
        super(message);
    }

    public static InvalidDateException getHttpException(String errorMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(errorMessage, InvalidDateException.class);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }
}
