package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.UnknownHttpStatusCodeException;

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

    public static NoSuchField getHttpException(UnknownHttpStatusCodeException e) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(e.getResponseBodyAsString(),NoSuchField.class);
    }
}
