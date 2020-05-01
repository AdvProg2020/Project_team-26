package model;

public class ProductSeller {

    private int id;
    private Product product;
    private Seller seller;
    private long price;
    private int remainingItems;

    public ProductSeller(int id, Product product) {
        this.id = id;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Seller getSeller() {
        return seller;
    }

    public long getPrice() {
        return price;
    }

    public int getRemainingItems() {
        return remainingItems;
    }
}
