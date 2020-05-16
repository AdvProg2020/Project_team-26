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

    public OffRequest() {
    }

    public int getId() {
        return id;
    }

    public RequestTpe getRequestTpe() {
        return requestTpe;
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
}
