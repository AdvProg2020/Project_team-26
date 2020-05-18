package model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "off_request")
public class OffRequest {

    @Id
    @Column(name = "off_request_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "off_id")
    private Off mainOff;

    @Column(name = "request_time")
    private Date requestTime;

    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "user_id")
    private Seller seller;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @OneToMany(mappedBy = "offRequest", cascade = CascadeType.ALL)
    private List<OffItemRequest> items;

    @Column(name = "request_type")
    private RequestType requestType;

    @Column(name = "request_status")
    private RequestStatus requestStatus;

    public OffRequest() {
        requestTime = new Date();
        requestStatus = RequestStatus.PENDING;
    }

    public OffRequest(Seller seller, Date startDate, Date endDate, RequestType requestType) {
        this.seller = seller;
        this.startDate = startDate;
        this.endDate = endDate;
        this.requestType = requestType;
        requestTime = new Date();
        requestStatus = RequestStatus.PENDING;
    }

    public int getId() {
        return id;
    }

    public Off getMainOff() {
        return mainOff;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public RequestType getRequestTpe() {
        return requestType;
    }

    public Seller getSeller() {
        return seller;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public List<OffItemRequest> getItems() {
        return items;
    }

    public void setMainOff(Off mainOff) {
        this.mainOff = mainOff;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
