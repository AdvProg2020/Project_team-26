package client.model.enums;

public enum Role {
    ADMIN, SELLER, CUSTOMER, SUPPORT;

    public static Role getRole(String role) {
        switch (role) {
            case "Seller":
                return SELLER;
            case "Manager":
                return ADMIN;
            case "Customer":
                return CUSTOMER;
            case "Support":
                return SUPPORT;
        }
        return null;
    }

    public static String getRoleType(Role role) {
        switch (role) {
            case CUSTOMER:
                return "Customer";
            case SELLER:
                return "Seller";
            case ADMIN:
                return "Manager";
            default:
                return "Support";
        }
    }
}
