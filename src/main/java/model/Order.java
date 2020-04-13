package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order implements Savable {

    private int id;
    private boolean isLoaded;
    private Date date;
    private long totalPrice;
    private long paidAmount;
    private Promo usedPromo;
    private List<OrderItem> items;

    public Order(int id) {
        this.id = id;
        items = new ArrayList<>();
    }

    @Override
    public void load() {
        if (!isLoaded) {

            isLoaded = true;
        }
    }

    @Override
    public void save() {

    }
}
