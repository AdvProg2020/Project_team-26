package model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FeatureType {
    INTEGER, STRING, DOUBLE;

    public static String getType(FeatureType type) {
        switch (type) {
            case DOUBLE:
                return "Double";
            case STRING:
                return "String";
            default:
                return "Integer";
        }

    }
}
