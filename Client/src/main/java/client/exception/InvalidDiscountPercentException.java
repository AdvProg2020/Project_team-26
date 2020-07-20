package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class InvalidDiscountPercentException extends Exception {

    public InvalidDiscountPercentException(String message) {
        super(message);
    }

    public static InvalidDiscountPercentException getHttpException(String errorMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(errorMessage, InvalidDiscountPercentException.class);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }
}
