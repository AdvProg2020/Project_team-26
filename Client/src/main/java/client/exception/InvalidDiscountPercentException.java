package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;

public class InvalidDiscountPercentException extends Exception {

    public InvalidDiscountPercentException(String message) {
        super(message);
    }

    public static InvalidDiscountPercentException getHttpException(UnknownHttpStatusCodeException e) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(e.getResponseBodyAsString(),InvalidDiscountPercentException.class);
    }
}
