package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class PasswordIsWrongException extends Exception {

    public PasswordIsWrongException(String message) {
        super(message);
    }

    public static PasswordIsWrongException getHttpException(String errorMessage){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(errorMessage, PasswordIsWrongException.class);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }
}
