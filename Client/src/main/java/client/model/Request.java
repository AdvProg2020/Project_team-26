package client.model;

import client.model.enums.RequestStatus;
import client.model.enums.RequestType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

public class Request {
    private int id;
    private User requestedBy;
    private Date requestTime;
    private RequestType requestType;
    private RequestStatus requestStatus;
    private Product product;
    private ProductSeller productSeller;
    private Off off;
    private OffItem offItem;
    private String type;
    //Only for edit
    private String fieldName;
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

    public Request(@JsonProperty("id") int id, @JsonProperty("requestedBy") User requestedBy,
                   @JsonProperty("requestTime") Date requestTime,
                   @JsonProperty("requestType") RequestType requestType,
                   @JsonProperty("requestStatus") RequestStatus requestStatus,
                   @JsonProperty("product") Product product,
                   @JsonProperty("productSeller") ProductSeller productSeller,
                   @JsonProperty("off") Off off,
                   @JsonProperty("offItem") OffItem offItem,
                   @JsonProperty("type") String type,
                   @JsonProperty("fieldName") String fieldName,
                   @JsonProperty("newValue") String newValue) {
        this.id = id;
        this.requestedBy = requestedBy;
        this.requestTime = requestTime;
        this.requestType = requestType;
        this.requestStatus = requestStatus;
        this.product = product;
        this.productSeller = productSeller;
        this.off = off;
        this.offItem = offItem;
        this.type = type;
        this.fieldName = fieldName;
        this.newValue = newValue;
    }

    @JsonCreator


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
        if (type.equals("Product")) {
            if (requestType == RequestType.ADD) {
                return product + "\n\n" + productSeller;
            } else if (requestType == RequestType.EDIT) {
                return product + "\n\n" + changeToString();
            } else {
                return product.toString();
            }
        } else if (type.equals("ProductSeller")) {
            if (requestType == RequestType.EDIT) {
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
