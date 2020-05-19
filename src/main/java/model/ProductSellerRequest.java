package model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product_seller_request")
public class ProductSellerRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_seller_request_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_request_id")
    private ProductRequest productRequest;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "product_seller_id")
    private ProductSeller mainProductSeller;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "request_time")
    private Date requestTime;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Column(name = "price", nullable = false)
    private long price;

    @Column(name = "remaining_items")
    private int remainingItems;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_type")
    private RequestType requestType;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status")
    private RequestStatus requestStatus;

    public ProductSellerRequest() {
        requestTime = new Date();
        requestStatus = RequestStatus.PENDING;
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

    public ProductSeller getProductSeller() {
        ProductSeller productSeller;
        if(this.mainProductSeller != null)
            productSeller = this.mainProductSeller;
        else {
            productSeller = new ProductSeller();
            productSeller.setProduct(product);
        }

        productSeller.setSeller(seller);
        productSeller.setPrice(price);
        productSeller.setRemainingItems(remainingItems);
        return productSeller;
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

    public ProductSeller getMainProductSeller() {
        return mainProductSeller;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setMainProductSeller(ProductSeller mainProductSeller) {
        this.mainProductSeller = mainProductSeller;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}