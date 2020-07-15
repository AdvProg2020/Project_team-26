package exception;

public class NotEnoughCreditException extends Exception {

    private long currentCredit;

    public NotEnoughCreditException(String message, long currentCredit) {
        super(message);
        this.currentCredit = currentCredit;
    }

    public long getCurrentCredit() {
        return currentCredit;
    }
}
