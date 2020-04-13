package model;

public class OrderItem implements Savable {

    private int id;
    private boolean isLoaded;
    private Product product;
    private int amount;
    private Seller seller;
    private long price;
    private long paidPrice;
    private ShipmentState state;

    public OrderItem(int id) {
        this.id = id;
    }

    @Override
    public void load() {
        if(!isLoaded) {

            isLoaded = true;
        }
    }

    @Override
    public void save() {

    }
}
