package controller;

public class Exceptions {
    public class InvalidUserNameException extends Exception {
        public InvalidUserNameException(String message) {
            super(message);
        }
    }

    public class InvalidPasswordException extends Exception {
        public InvalidPasswordException(String message) {
            super(message);
        }
    }

    public class UnSuccessfulLogout extends Exception {
        public UnSuccessfulLogout(String message) {
            super(message);
        }
    }

}
