package controller.interfaces.discount;

import controller.discount.PromoDetailsController;

public interface PromoController {

    PromoDetailsController getPromoCodeTemplate();

    void createPromoCode(String promoCode, String token);

    void editPromoCode(int oldPromoCodeId, PromoDetailsController newCode, String token);

    void removePromoCode(int promoCodeId, String token);
}
