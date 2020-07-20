package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class NotBoughtTheProductException extends Exception {

    public NotBoughtTheProductException(String message) {
        super(message);
    }

    public static NotBoughtTheProductException getHttpException(String errorMessage)  {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(errorMessage, NotBoughtTheProductException.class);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }
}
