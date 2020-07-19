package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;

public class NoObjectIdException extends Exception {

    public  NoObjectIdException(String message) {
        super(message);
    }

    public static NoObjectIdException getHttpException(UnknownHttpStatusCodeException e) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(e.getResponseBodyAsString(),NoObjectIdException.class);
    }
}
