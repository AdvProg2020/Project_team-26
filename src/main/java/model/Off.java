package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Off implements Savable {

    private int id;
    private boolean isLoaded;
    private Date startDate;
    private Date endDate;
    private List<OffItem> items;

    public Off(int id) {
        this.id = id;
        items = new ArrayList<>();
    }

    @Override
    public void load() {
        if(!isLoaded) {

            isLoaded = true;
        }
    }

    @Override
    public void save() {

    }
}
