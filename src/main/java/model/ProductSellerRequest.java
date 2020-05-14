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

    public ProductSellerRequest() {
    }
}