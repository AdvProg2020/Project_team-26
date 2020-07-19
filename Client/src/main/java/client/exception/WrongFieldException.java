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

    public static WrongFieldException getHttpException(String errorMessage) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(errorMessage,WrongFieldException.class);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }
}
