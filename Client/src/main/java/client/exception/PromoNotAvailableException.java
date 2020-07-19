package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;

public class PromoNotAvailableException extends Exception {

    public PromoNotAvailableException(String message) {
        super(message);
    }

    public static PromoNotAvailableException getHttpException(String errorMessage) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(errorMessage, PromoNotAvailableException.class);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }
}
