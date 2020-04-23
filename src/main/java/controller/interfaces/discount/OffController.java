package controller.interfaces.discount;

import java.util.Date;

public interface OffController {

    int createNewOff(String token);

    void addProductToOff(int id, int productId,String token);

    void removeProductFromOff(int id, int productId, String token);

    void setStartDate(int id, Date date, String token);

    void setEndDate(int id, Date date, String token);

    void removeAOff(int id, String token);
}
