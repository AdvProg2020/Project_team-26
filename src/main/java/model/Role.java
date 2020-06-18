package model;

public enum Role {
    ADMIN, SELLER, CUSTOMER;

    public static Role getRole(String role) {
        switch (role) {
            case "Seller":
                return SELLER;
            case "Manager":
                return ADMIN;
            case "Customer":
                return CUSTOMER;
        }
        return null;
    }
}
