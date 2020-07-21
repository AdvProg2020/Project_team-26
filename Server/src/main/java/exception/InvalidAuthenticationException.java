package exception;

public class InvalidAuthenticationException extends Exception {
    private String field;

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }


    public InvalidAuthenticationException(String message , String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
