package client.model;

import client.model.enums.RequestType;
import client.model.enums.Status;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "off")
public class Off {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "off_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "user_id")
    private Seller seller;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @OneToMany(mappedBy = "off", cascade = CascadeType.ALL)
    private List<OffItem> items;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public Off() {
        items = new ArrayList<OffItem>();

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

    public Seller getSeller() {
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

    public void setSeller(Seller seller) {
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

    public OffRequest createRequest(RequestType requestType) {
        OffRequest request = new OffRequest(seller, startDate, endDate, requestType);
        for (OffItem item : items) {
            OffItemRequest offItemRequest = new OffItemRequest(request, item.getProductSeller(), item.getPriceInOff());
        }
        return request;
    }

    @Override
    public Off clone() {
        Off newOff = new Off();
        newOff.setId(this.id);
        newOff.setSeller(seller);
        newOff.setItems(items);
        return newOff;
    }

    @Override
    public String toString() {
        return "Id: " + id + "\n" +
                "Seller: " + seller.getFullName() + "\n" +
                "With Username: " + seller.getUsername() + "\n" +
                "Start Date: " + startDate + "\n" +
                "End Date: " + endDate + "\n" + itemsToString();
    }

    private String itemsToString() {
        String result = "";
        for (OffItem item : items) {
            result += "    Product Name: " + item.getProductSeller().getProduct().getName() +
                    "    Price In Off: " + item.getPriceInOff();
        }
        return result;
    }
}
