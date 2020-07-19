package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;

public class NotLoggedINException extends Exception {
    public NotLoggedINException(String message) {
        super(message);
    }

    public static NotLoggedINException getHttpException(UnknownHttpStatusCodeException e) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(e.getResponseBodyAsString(),NotLoggedINException.class);
    }
}
