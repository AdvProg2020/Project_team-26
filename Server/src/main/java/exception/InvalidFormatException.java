package exception;

public class InvalidFormatException extends Exception {

    private String fieldName;

    public InvalidFormatException(String message,String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
