package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ObjectAlreadyExistException extends Exception {

    Object object;

    public ObjectAlreadyExistException(String message, Object object) {
        super(message);
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public static ObjectAlreadyExistException getHttpException(String errorMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(errorMessage, ObjectAlreadyExistException.class);
        } catch (IOException e) {
            return new ObjectAlreadyExistException("ObjectAlreadyExist", new Object());
        }
    }
}
