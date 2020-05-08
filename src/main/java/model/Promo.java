package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Promo {

    private int id;
    private String promoCode;
    private Date startDate;
    private Date endDate;
    private double percent;
    private long maxDiscount;
    private int maxValidUse;
    private List<Customer> customers;

    public Promo(String code) {
        this.promoCode = code;
        customers = new ArrayList<Customer>();
    }

    public int getId() {
        return id;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public double getPercent() {
        return percent;
    }

    public long getMaxDiscount() {
        return maxDiscount;
    }

    public int getMaxValidUse() {
        return maxValidUse;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public void setMaxDiscount(long maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public boolean isAvailable() {
        // TODO: This function must tell if promo is available now
        return false;
    }
}
