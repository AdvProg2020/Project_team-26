package model;

import javax.persistence.*;

@Entity
@Table(name = "product_seller_request")
public class ProductSellerRequest {

    @Id
    @Column(name="product_seller_request_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_request_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Column(name = "price", nullable = false)
    private long price;

    @Column(name = "remaining_items")
    private int remainingItems;

    @Column(name = "request_type")
    private RequestTpe requestTpe;

    private RequestStatus requestStatus;

    public ProductSellerRequest() {
    }

    public ProductSellerRequest(Product product, Seller seller, long price, int remainingItems, RequestTpe requestType) {
        this.product = product;
        this.seller = seller;
        this.price = price;
        this.remainingItems = remainingItems;
        this.requestTpe = requestType;
        requestStatus = RequestStatus.PENDING;
    }

    public void setRequestTpe(RequestTpe requestTpe) {
        this.requestTpe = requestTpe;
    }
}