package model;

public class ProductSeller implements Savable {

    private int id;
    private boolean isLoaded;
    private Product product;
    private Seller seller;
    private long price;
    private int remainingItems;

    public ProductSeller(int id, Product product) {
        this.id = id;
        this.product = product;
    }

    @Override
    public void load() {
        if (!isLoaded) {

            isLoaded = true;
        }
    }

    @Override
    public void save() {

    }
}
