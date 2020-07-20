package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoSuchField extends Exception {
    List<String> wrongFields;
    public NoSuchField(String message) {
        super(message);
        wrongFields = new ArrayList<>();
    }

    public void addAFields(List<String> allWrongFields) {
        wrongFields = allWrongFields;
    }

    public List<String> getWrongFields() {
        return wrongFields;
    }

    public static NoSuchField getHttpException(String errorMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(errorMessage, NoSuchField.class);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }
}
