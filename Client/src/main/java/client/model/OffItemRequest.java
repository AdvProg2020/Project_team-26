package client.model;

import javax.persistence.*;

@Entity
@Table(name = "off_details_request")
public class OffItemRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "off_details_request_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "off_request_id")
    private OffRequest offRequest;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductSeller productSeller;

    @Column(name = "price_in_off")
    private long priceInOff;

    public OffItemRequest() {
    }

    public OffItemRequest(OffRequest offRequest, ProductSeller productSeller, long priceInOff) {
        this.offRequest = offRequest;
        this.productSeller = productSeller;
        this.priceInOff = priceInOff;
    }

    public ProductSeller getProductSeller() {
        return productSeller;
    }

    public long getPriceInOff() {
        return priceInOff;
    }
}
