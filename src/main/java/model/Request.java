package model;

import model.enums.RequestStatus;
import model.enums.RequestType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User requestedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "request_time")
    private Date requestTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_type")
    private RequestType requestType;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status")
    private RequestStatus requestStatus;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_seller_id")
    private ProductSeller productSeller;

    @ManyToOne
    @JoinColumn(name = "off_id")
    private Off off;

    @ManyToOne
    @JoinColumn(name = "off_details_id")
    private OffItem offItem;

    @Column(name = "type")
    private String type;

    //Only for edit
    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "new_value")
    private String newValue;

    public Request() {
    }

    public Request(User requestedBy, Date requestTime,
                   RequestType requestType, RequestStatus requestStatus) {
        this.requestedBy = requestedBy;
        this.requestTime = requestTime;
        this.requestType = requestType;
        this.requestStatus = requestStatus;
    }

    public void setForEdit(String fieldName, String newValue) {
        this.fieldName = fieldName;
        this.newValue = newValue;
    }

    public void setProduct(Product product) {
        this.type = "Product";
        this.product = product;
    }

    public void setProductSeller(ProductSeller productSeller) {
        this.type = "ProductSeller";
        this.productSeller = productSeller;
    }

    public void setOff(Off off) {
        this.type = "Off";
        this.off = off;
    }

    public void setOffItem(OffItem offItem) {
        this.type = "OffDetail";
        this.offItem = offItem;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public User getRequestedBy() {
        return requestedBy;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public Product getProduct() {
        return product;
    }

    public ProductSeller getProductSeller() {
        return productSeller;
    }

    public Off getOff() {
        return off;
    }

    public OffItem getOffItem() {
        return offItem;
    }

    public String getType() {
        return type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Override
    public String toString() {
        return "Request Id: " + id + "\n" +
                "Requested By: " + requestedBy.getFullName() + "\n" +
                "With Username: " + requestedBy.getUsername() + "\n" +
                "Date: " + requestTime + "\n" +
                "Request Type: " + requestType + "\n" +
                "Change Type: " + type + "\n\n" + requestToString();


    }

    private String requestToString() {
        if(type.equals("Product")) {
            if(requestType == RequestType.ADD) {
                return product + "\n\n" + productSeller;
            } else if(requestType == RequestType.EDIT) {
                return product + "\n\n" + changeToString();
            } else {
                return product.toString();
            }
        } else if(type.equals("ProductSeller")) {
            if(requestType == RequestType.EDIT) {
                return productSeller + "\n\n" + changeToString();
            } else {
                return productSeller.toString();
            }
        } else {
            return off.toString();
        }
    }

    private String changeToString() {
        return "Changed Field: " + fieldName + "\n" +
                "New Value: " + newValue;
    }
}
