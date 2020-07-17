package client.exception;

public class WrongFieldException extends Exception{
    String fieldName;
    public WrongFieldException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }
}
