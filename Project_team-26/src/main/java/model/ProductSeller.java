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
}
