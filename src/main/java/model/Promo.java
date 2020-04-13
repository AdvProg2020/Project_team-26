package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Promo implements Savable {

    private int id;
    private boolean isLoaded;
    private String promoCode;
    private Date startDate;
    private Date endDate;
    private double percent;
    private long maxDiscount;
    private int maxValidUse;
    private List<Customer> customers;

    public Promo(int id) {
        this.id = id;
        customers = new ArrayList<>();
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
