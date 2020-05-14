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

    public OrderItem(Product product, int amount, Seller seller, long price, long paidPrice, ShipmentState state) {
        this.product = product;
        this.amount = amount;
        this.seller = seller;
        this.price = price;
        this.paidPrice = paidPrice;
        this.state = state;
    }

    public Seller getSeller() {
        return this.seller;
    }

    public long getPrice() {
        return this.price;
    }

    public int getId() {
        return this.id;
    }

    public long getPaidPrice() {
        return paidPrice;
    }

    public int getProductId() {
        return product.getId();
    }

    public Product getProduct() {
        return product;
    }

    public int getAmount() {
        return amount;
    }
}
