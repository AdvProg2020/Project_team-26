package controller.interfaces.discount;

import java.time.LocalDateTime;

public interface PromoDetailsController {

    String getPromoCode(String token);

    LocalDateTime getStartDate(String token);

    void setStartDate(LocalDateTime startDate, String token);

    LocalDateTime getEndDate(String token);

    void setEndDate(LocalDateTime endDate, String token);

    double getPercent(String token);

    void setPercent(double percent, String token);

    long getMaxDiscount(String token);

    void setMaxDiscount(long maxDiscount, String token);

    int getMaxValidUse(String token);

    void setMaxValidUse(int maxValidUse, String token);

    int[] getCustomersIds();

    void addCustomer(int customersId, String token);
}
