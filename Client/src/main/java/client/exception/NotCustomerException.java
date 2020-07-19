package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;

public class NotCustomerException extends Exception {

    public NotCustomerException(String message) {
        super(message);
    }

    public static NotCustomerException getHttpException(String errorMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(errorMessage, NotCustomerException.class);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }
}
