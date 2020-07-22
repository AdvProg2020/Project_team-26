package model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Currency;

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

    @JsonCreator
    public static Role getRoleFromString(String type) {
        switch (type) {
            case "CUSTOMER":
                return CUSTOMER;
            case "SELLER":
                return SELLER;
            case "ADMIN":
                return ADMIN;
            default:
                return SUPPORT;
        }
    }
}
