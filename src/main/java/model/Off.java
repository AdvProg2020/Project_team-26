package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "off")
public class Off {

    @Id
    @Column(name = "off_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "user_id")
    private Seller seller;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @OneToMany(mappedBy = "off", cascade = CascadeType.ALL)
    private List<OffItem> items;

    public Off() {
    }

    public Off(String stringCode) {
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

    public List<OffItem> getItems() {
        return items;
    }

    public OffItem getItemByProductId(int id) {
        for (OffItem item : items) {
            if (item.getProduct().getId() == id)
                return item;
        }
        return null;
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
}
