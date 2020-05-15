package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "promo")
public class Promo {

    @Id
    @Column(name = "promo_id", unique = true)
    private int id;

    @Column(name = "promo_code", unique = true)
    private String promoCode;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "discount_percent", nullable = false)
    private double percent;

    @Column(name = "max_discount", nullable = false)
    private long maxDiscount;

    @Column(name = "max_valid_use", nullable = false)
    private int maxValidUse;

    @ManyToMany
    @JoinTable(
            name = "user_promo",
            joinColumns = @JoinColumn(name = "promo_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<Customer> customers;

    public Promo() {
    }

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

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setMaxValidUse(int maxValidUse) {
        this.maxValidUse = maxValidUse;
    }
}
