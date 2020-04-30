package controller;

public class Exceptions {
    public class FieldsExistWithSameName extends Exception {
        public FieldsExistWithSameName(String message) {
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

    public class InvalidAccessDemand extends Exception {
        public InvalidAccessDemand(String message) {
            super(message);
        }
    }

    public class TheParameterDoesNOtExist extends Exception {
        public TheParameterDoesNOtExist(String message) {
            super(message);
        }
    }
}
