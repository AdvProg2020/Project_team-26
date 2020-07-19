package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.UnknownHttpStatusCodeException;

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

    public static NotEnoughCreditException getHttpException(UnknownHttpStatusCodeException e) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(e.getResponseBodyAsString(),NotEnoughCreditException.class);
    }
}
