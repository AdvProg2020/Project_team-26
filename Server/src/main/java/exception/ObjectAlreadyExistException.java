package exception;

public class ObjectAlreadyExistException extends Exception {

    Object object;

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    public ObjectAlreadyExistException(String message, Object object) {
        super(message);
        this.object = object;
    }

    public Object getObject() {
        return object;
    }
}
