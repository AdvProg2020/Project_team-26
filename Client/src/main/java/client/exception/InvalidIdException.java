package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class InvalidIdException extends Exception {
    public InvalidIdException(String message) {
        super(message);
    }

    public static InvalidIdException getHttpException(String errorMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(errorMessage, InvalidIdException.class);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }


}
