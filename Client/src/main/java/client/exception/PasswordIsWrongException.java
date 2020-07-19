package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;

public class PasswordIsWrongException extends Exception {

    public PasswordIsWrongException(String message) {
        super(message);
    }

    public static PasswordIsWrongException getHttpException(UnknownHttpStatusCodeException e) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(e.getResponseBodyAsString(),PasswordIsWrongException.class);
    }
}
