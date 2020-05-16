package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product_seller_request")
public class ProductSellerRequest {

    @Id
    @Column(name="product_seller_request_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_request_id")
    private ProductSeller productRequest;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "request_time")
    private Date requestTime;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Column(name = "price", nullable = false)
    private long price;

    @Column(name = "remaining_items")
    private int remainingItems;

    @Column(name = "request_type")
    private RequestType requestType;

    @Column(name = "request_status")
    private RequestStatus requestStatus;

    public ProductSellerRequest() {
    }

    public ProductSellerRequest(Product product, Seller seller, long price, int remainingItems, RequestType requestType) {
        this.product = product;
        this.seller = seller;
        this.price = price;
        this.remainingItems = remainingItems;
        this.requestType = requestType;
        requestStatus = RequestStatus.PENDING;
        requestTime = new Date();
    }

    public RequestType getRequestType() {
        return requestType;
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
}