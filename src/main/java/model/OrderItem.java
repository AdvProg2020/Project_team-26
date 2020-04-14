package model;

public class OrderItem {

    private int id;
    private Product product;
    private int amount;
    private Seller seller;
    private long price;
    private long paidPrice;
    private ShipmentState state;

    public OrderItem(int id) {
        this.id = id;
    }
}
