package client.model;

import client.model.enums.RequestStatus;
import client.model.enums.RequestType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Request {
    private int id;
    private User requestedBy;
    private Date requestTime;
    private RequestType requestType;
    private RequestStatus requestStatus;
    private String type;
    //Only for edit
    private String fieldName;
    private String newValue;

    @JsonCreator
    public Request(@JsonProperty("id") int id, @JsonProperty("requestedBy") User requestedBy,
                   @JsonProperty("requestTime") Date requestTime,
                   @JsonProperty("requestType") RequestType requestType,
                   @JsonProperty("requestStatus") RequestStatus requestStatus,
                   @JsonProperty("type") String type,
                   @JsonProperty("fieldName") String fieldName,
                   @JsonProperty("newValue") String newValue) {
        this.id = id;
        this.requestedBy = requestedBy;
        this.requestTime = requestTime;
        this.requestType = requestType;
        this.requestStatus = requestStatus;
        this.type = type;
        this.fieldName = fieldName;
        this.newValue = newValue;
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

    public String getType() {
        return type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getNewValue() {
        return newValue;
    }
}
