package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class NotEnoughCreditException extends Exception {

    private long currentCredit;

    public NotEnoughCreditException(String message, long currentCredit) {
        super(message);
        this.currentCredit = currentCredit;
    }

    public long getCurrentCredit() {
        return currentCredit;
    }

    public static NotEnoughCreditException getHttpException(String errorMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(errorMessage, NotEnoughCreditException.class);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }
}
