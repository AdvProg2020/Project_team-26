package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;

public class AlreadyLoggedInException extends Exception {

    public AlreadyLoggedInException(String message) {
        super(message);
    }

    public static AlreadyLoggedInException getHttpException(String errorMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(errorMessage,AlreadyLoggedInException.class);
        }
        catch (IOException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }
}
