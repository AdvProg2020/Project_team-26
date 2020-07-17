package client.exception;

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
}
