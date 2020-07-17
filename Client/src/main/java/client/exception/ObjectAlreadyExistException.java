package client.exception;

public class ObjectAlreadyExistException extends Exception {

    Object object;

    public ObjectAlreadyExistException(String message, Object object) {
        super(message);
        this.object = object;
    }

    public Object getObject() {
        return object;
    }
}
