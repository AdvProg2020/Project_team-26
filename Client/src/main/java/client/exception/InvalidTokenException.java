package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;

public class InvalidTokenException extends Exception {

    public InvalidTokenException(String message) {
        super(message);
    }

    public static InvalidTokenException getHttpException(UnknownHttpStatusCodeException e) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(e.getResponseBodyAsString(),InvalidTokenException.class);
    }
}
