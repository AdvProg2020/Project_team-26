package client.exception;

public class InvalidAuthenticationException extends Exception {
    private String field;

    public InvalidAuthenticationException(String message , String field) {
        super(message);
        this.field = field;
    }

    public String getFieldName() {
        return field;
    }
}
