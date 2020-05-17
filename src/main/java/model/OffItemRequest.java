package model;

import javax.persistence.*;

@Entity
@Table(name = "off_details_request")
public class OffItemRequest {

    @Id
    @Column(name = "off_details_request_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "off_request_id")
    private OffRequest offRequest;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "price_in_off")
    private long priceInOff;

    public OffItemRequest() {
    }

    public OffItemRequest(OffRequest offRequest, Product product, long priceInOff) {
        this.offRequest = offRequest;
        this.product = product;
        this.priceInOff = priceInOff;
    }

    public Product getProduct() {
        return product;
    }

    public long getPriceInOff() {
        return priceInOff;
    }
}
