package controller.discount;

public class PromoController implements controller.interfaces.discount.PromoController {

    public PromoDetailsController getPromoCodeTemplate() {
        return new PromoDetailsController();
    }

    public void createPromoCode(String promoCode, String token) {

    }

    public void editPromoCode(int oldPromoCodeId, PromoDetailsController newCode, String token) {

    }

    public void removePromoCode(int promoCodeId, String token) {

    }
}
