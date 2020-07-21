package client.model;

import client.model.enums.RequestType;
import client.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Off {
    private int id;
    private User seller;
    private Date startDate;
    private Date endDate;
    private List<OffItem> items;
    private Status status;

    public Off() {
        items = new ArrayList<>();
    }

    @JsonCreator
    public Off(@JsonProperty("id") int id,
               @JsonProperty("seller") User seller,
               @JsonProperty("startDate") Date startDate,
               @JsonProperty("endDate") Date endDate,
               @JsonProperty("items") List<OffItem> items,
               @JsonProperty("status") Status status) {
        this.id = id;
        this.seller = seller;
        this.startDate = startDate;
        this.endDate = endDate;
        this.items = items;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public User getSeller() {
        return seller;
    }

    public Status getStatus() {
        return status;
    }

    public List<OffItem> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj instanceof Off))
            return false;
        Off off = (Off) obj;
        if (off.getId() == this.getId())
            return true;
        return false;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setItems(List<OffItem> items) {
        this.items = items;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public Off clone() {
        Off newOff = new Off();
        newOff.setId(this.id);
        newOff.setSeller(seller);
        newOff.setItems(items);
        return newOff;
    }
}
