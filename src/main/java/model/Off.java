package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Off {

    private int id;
    private String stringCode;
    private boolean isLoaded;
    private Date startDate;
    private Date endDate;
    private List<OffItem> items;

    public Off(String stringCode) {
        this.stringCode = stringCode;
        items = new ArrayList<OffItem>();
    }

    public int getId() {
        return id;
    }

    public String getStringCode() {
        return stringCode;
    }

    public boolean isLoaded() {
        return isLoaded;
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
}
