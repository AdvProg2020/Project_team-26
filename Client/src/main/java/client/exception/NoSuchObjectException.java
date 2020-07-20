package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class NoSuchObjectException extends Exception {
    public NoSuchObjectException(String message) {
        super(message);
    }


    public static NoSuchObjectException getHttpException(String errorMessage)  {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(errorMessage, NoSuchObjectException.class);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }
}
