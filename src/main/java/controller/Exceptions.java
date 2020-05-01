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

    public static class UnSuccessfulLogout extends Exception {
        public UnSuccessfulLogout(String message) {
            super(message);
        }
    }

    public class InvalidFiledException extends Exception {
        public InvalidFiledException(String message) {
            super(message);
        }
    }

    public class InvalidDeleteDemand extends Exception {
        public InvalidDeleteDemand(String message) {
            super(message);
        }
    }

    public static class InvalidAccessDemand extends Exception {
        public InvalidAccessDemand(String message) {
            super(message);
        }
    }

    public class UserNameDoesntExist extends Exception {
        public UserNameDoesntExist(String message) {
            super(message);
        }
    }
    public class PromoCodeDoesntExist extends Exception {
        public PromoCodeDoesntExist(String message) {
            super(message);
        }
    }
    public class FieldsDoesNotExist extends Exception{
        public FieldsDoesNotExist(String message) {
            super(message);
        }
    }
    public static class IncorrectUsernameFormat extends Exception {
        public IncorrectUsernameFormat(String message) {
            super(message);
        }
    }
    public static class IncorrectPasswordFormat extends Exception {
        public IncorrectPasswordFormat(String message) {
            super(message);
        }
    }
    public static class UsernameAlreadyExists extends Exception {
        public UsernameAlreadyExists(String message) {
            super(message);
        }
    }

}
