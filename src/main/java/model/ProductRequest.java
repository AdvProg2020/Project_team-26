package model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product_request")
public class ProductRequest {

    @Id
    @Column(name = "product_request_id", unique = true)
    private int id;

    @ManyToOne
    @Column(name = "main_product_id")
    private Product mainProduct;

    @ManyToOne
    @JoinColumn(name = "requested_by_id")
    private Seller requestedBy;

    @Column(name = "request_time")
    private Date requestTime;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductSellerRequest> sellerList;

    @Column(name = "request_type")
    private RequestType requestType;

    @Column(name = "request_status")
    private RequestStatus requestStatus;

    public ProductRequest() {
    }

    public ProductRequest(String name, String brand, String description, Category category, RequestType requestType) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.category = category;
        this.requestType = requestType;
        requestStatus = RequestStatus.PENDING;
        requestTime = new Date();
    }

    public Product getProduct() {
        if(mainProduct != null) {
            mainProduct.setName(name);
            mainProduct.setBrand(brand);
            mainProduct.setDescription(description);
            mainProduct.setCategory(category);
            return mainProduct;
        } else {
            Product product = new Product(name, brand, description);
            product.setCategory(category);
            ProductSeller productSeller = sellerList.get(0).getProductSeller();
            productSeller.setProduct(product);
            product.addSeller(productSeller);
            return product;
        }
    }

    public RequestType getRequestTpe() {
        return requestType;
    }

    public int getId() {
        return id;
    }

    public Product getMainProduct() {
        return mainProduct;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public List<ProductSellerRequest> getSellerList() {
        return sellerList;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void addSeller(ProductSellerRequest productSellerRequest) {
        sellerList.add(productSellerRequest);
    }

    public Seller getRequestedBy() {
        return requestedBy;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setMainProduct(Product mainProduct) {
        this.mainProduct = mainProduct;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
