package model;

import javax.persistence.*;

@Entity
@Table(name = "off_details")
public class OffItem {

    @Id
    @Column(name = "off_details_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "off_id")
    private Off off;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "price_in_off")
    private long priceInOff;

    public OffItem() {
    }

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
