package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;

public class InvalidAuthenticationException extends Exception {
    private String field;

    public InvalidAuthenticationException(String message , String field) {
        super(message);
        this.field = field;
    }

    public String getFieldName() {
        return field;
    }

    public static InvalidAuthenticationException getHttpException(UnknownHttpStatusCodeException e) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(e.getResponseBodyAsString(),InvalidAuthenticationException.class);
    }
}
