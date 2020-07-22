package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class InvalidFormatException extends Exception {

    private String fieldName;

    public InvalidFormatException(String message,String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public static InvalidFormatException getHttpException(String errorMessage){
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(errorMessage);
        try {
            return objectMapper.readValue(errorMessage,InvalidFormatException.class);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }
}
