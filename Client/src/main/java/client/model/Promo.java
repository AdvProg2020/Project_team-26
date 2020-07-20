package client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Promo {
    private int id;
    private String promoCode;
    private Date startDate;
    private Date endDate;
    private double percent;
    private long maxDiscount;
    private int maxValidUse;
    private List<Customer> customers;

    public Promo() {
        customers = new ArrayList<>();
    }

    public Promo(String code, Customer customer) {
        this.promoCode = code;
        customers = new ArrayList<Customer>();
        customers.add(customer);
    }

    @JsonCreator
    public Promo(@JsonProperty("id") int id,
                 @JsonProperty("promoCode") String promoCode,
                 @JsonProperty("startDate") Date startDate,
                 @JsonProperty("endDate") Date endDate,
                 @JsonProperty("percent") double percent,
                 @JsonProperty("maxDiscount") long maxDiscount,
                 @JsonProperty("maxValidUse") int maxValidUse) {
        this.id = id;
        this.promoCode = promoCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percent = percent;
        this.maxDiscount = maxDiscount;
        this.maxValidUse = maxValidUse;
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
        return true;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promo promo = (Promo) o;
        return id == promo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
