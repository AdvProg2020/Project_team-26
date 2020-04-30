package controller.interfaces.discount;

import java.util.Date;

public interface IPromoDetailsController {

    String getPromoCode(String token);

    Date getStartDate(String token);

    void setStartDate(Date startDate, String token);

    Date getEndDate(String token);

    void setEndDate(Date endDate, String token);

    double getPercent(String token);

    void setPercent(double percent, String token);

    long getMaxDiscount(String token);

    void setMaxDiscount(long maxDiscount, String token);

    int getMaxValidUse(String token);

    void setMaxValidUse(int maxValidUse, String token);

    int[] getCustomersIds();

    void addCustomer(int customersId, String token);
}
