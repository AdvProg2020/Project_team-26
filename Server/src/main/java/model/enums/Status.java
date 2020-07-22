package model.enums;

public enum Status {
    ACTIVE, DEACTIVE;



    public static Status getStatusFromString(String value) {
        switch (value) {
            case "ACTIVE":
                return ACTIVE;
            case "DEACTIVE":
                return DEACTIVE;
            default:
                return null;
        }
    }

}
