package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;

public class PromoNotAvailableException extends Exception {

    public PromoNotAvailableException(String message) {
        super(message);
    }

    public static PromoNotAvailableException getHttpException(UnknownHttpStatusCodeException e) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(e.getResponseBodyAsString(),PromoNotAvailableException.class);
    }
}
