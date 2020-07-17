package client.exception;

public class InvalidFormatException extends Exception {

    private String fieldName;

    public InvalidFormatException(String message,String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return this.fieldName;
    }
}
