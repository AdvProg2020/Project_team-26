package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;

public class NotBoughtTheProductException extends Exception {

    public NotBoughtTheProductException(String message) {
        super(message);
    }

    public static NotBoughtTheProductException getHttpException(UnknownHttpStatusCodeException e) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(e.getResponseBodyAsString(),NotBoughtTheProductException.class);
    }
}
