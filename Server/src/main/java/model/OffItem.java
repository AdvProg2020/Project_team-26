package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "off_details")
public class OffItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "off_details_id")
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "off_id")
    private Off off;

    @ManyToOne
    @JoinColumn(name = "product_seller_id")
    private ProductSeller productSeller;

    @Column(name = "price_in_off")
    private long priceInOff;

    public OffItem() {
    }

    public OffItem(ProductSeller productSeller, long priceInOff) {
        this.priceInOff = priceInOff;
        this.productSeller = productSeller;
    }

    public OffItem(ProductSeller productSeller, long priceInOff, Off off) {
        this.priceInOff = priceInOff;
        this.productSeller = productSeller;
        this.off = off;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ProductSeller getProductSeller() {
        return productSeller;
    }

    public long getPriceInOff() {
        return priceInOff;
    }

    public void setProductSeller(ProductSeller productSeller) {
        this.productSeller = productSeller;
    }

    public void setPriceInOff(long priceInOff) {
        this.priceInOff = priceInOff;
    }

    public void setOff(Off off) {
        this.off = off;
    }
}
