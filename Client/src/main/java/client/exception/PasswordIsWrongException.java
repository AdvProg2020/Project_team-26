package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;

public class PasswordIsWrongException extends Exception {

    public PasswordIsWrongException(String message) {
        super(message);
    }

    public static PasswordIsWrongException getHttpException(String errorMessage) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(errorMessage, PasswordIsWrongException.class);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }
}
