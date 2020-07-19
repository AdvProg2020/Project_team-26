package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;

public class NoSuchObjectException extends Exception {
    public NoSuchObjectException(String message) {
        super(message);
    }


    public static NoSuchObjectException getHttpException(UnknownHttpStatusCodeException e) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(e.getResponseBodyAsString(),NoSuchObjectException.class);
    }
}
