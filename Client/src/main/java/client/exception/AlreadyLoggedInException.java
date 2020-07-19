package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;

public class AlreadyLoggedInException extends Exception {

    public AlreadyLoggedInException(String message) {
        super(message);
    }

    public static AlreadyLoggedInException getHttpException(UnknownHttpStatusCodeException e) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(e.getResponseBodyAsString(),AlreadyLoggedInException.class);
    }
}
