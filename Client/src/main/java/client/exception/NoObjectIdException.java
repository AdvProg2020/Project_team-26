package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class NoObjectIdException extends Exception {

    public  NoObjectIdException(String message) {
        super(message);
    }

    public static NoObjectIdException getHttpException(String errorMessage)  {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(errorMessage, NoObjectIdException.class);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }
}
