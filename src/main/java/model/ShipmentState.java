package model;

public enum ShipmentState {
    WAITING_TO_SEND, SENT, RECEIVED;


    public static String getState(ShipmentState shipmentState) {
        switch (shipmentState) {
            case WAITING_TO_SEND:
                return "waiting";
            case SENT:
                return "sent";
            default:
                return "received";
        }
    }
}
