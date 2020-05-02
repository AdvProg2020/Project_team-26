package model;

public class OffItem {

    private int id;
    private Product product;
    private long priceInOff;

    public OffItem(Product product, long priceInOff) {
        this.priceInOff = priceInOff;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public long getPriceInOff() {
        return priceInOff;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setPriceInOff(long priceInOff) {
        this.priceInOff = priceInOff;
    }
}
