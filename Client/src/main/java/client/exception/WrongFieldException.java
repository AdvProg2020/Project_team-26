package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;

public class WrongFieldException extends Exception{
    String fieldName;
    public WrongFieldException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    public static WrongFieldException getHttpException(UnknownHttpStatusCodeException e) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(e.getResponseBodyAsString(),WrongFieldException.class);
    }
}
