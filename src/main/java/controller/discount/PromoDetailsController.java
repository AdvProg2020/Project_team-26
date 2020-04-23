package controller.discount;

import java.time.LocalDateTime;

public class PromoDetailsController implements controller.interfaces.discount.PromoDetailsController {

    public String getPromoCode(String token) {
        return null;
    }

    public LocalDateTime getStartDate(String token) {
        return null;
    }

    public void setStartDate(LocalDateTime startDate, String token) {

    }

    public LocalDateTime getEndDate(String token) {
        return null;
    }

    public void setEndDate(LocalDateTime endDate, String token) {

    }

    public double getPercent(String token) {
        return 0;
    }

    public void setPercent(double percent, String token) {

    }

    public long getMaxDiscount(String token) {
        return 0;
    }

    public void setMaxDiscount(long maxDiscount, String token) {

    }

    public int getMaxValidUse(String token) {
        return 0;
    }

    public void setMaxValidUse(int maxValidUse, String token) {

    }

    public int[] getCustomersIds() {
        return null;
    }

    public void addCustomer(int customersId, String token) {

    }
}
