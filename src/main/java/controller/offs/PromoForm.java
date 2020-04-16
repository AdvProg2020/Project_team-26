package controller.offs;

import model.Customer;

import java.util.Date;
import java.util.List;

public class PromoForm {

    private String promoCode;
    private Date startDate;
    private Date endDate;
    private double percent;
    private long maxDiscount;
    private int maxValidUse;
    private int[] customersIds;

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public long getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(long maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public int getMaxValidUse() {
        return maxValidUse;
    }

    public void setMaxValidUse(int maxValidUse) {
        this.maxValidUse = maxValidUse;
    }

    public int[] getCustomersIds() {
        return customersIds;
    }

    public void setCustomersIds(int[] customersIds) {
        this.customersIds = customersIds;
    }
}
