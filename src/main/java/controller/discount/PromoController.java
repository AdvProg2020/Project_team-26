package controller.discount;

import controller.Exceptions;
import controller.interfaces.discount.IPromoController;

import java.util.ArrayList;

public class PromoController implements IPromoController {

    @Override
    public PromoDetailsController getPromoCodeTemplate(String codeId, String token) throws Exceptions.TheParameterDoesNOtExist {
        return null;
    }

    @Override
    public PromoDetailsController[] getAllPromoCode(String token) {
        return new PromoDetailsController[0];
    }

    public void createPromoCode(String promoCode, String token) {

    }

    @Override
    public void editPromoCode(String oldPromoCodeId, ArrayList<String> fieldAndChanges, String token) throws Exceptions.TheParameterDoesNOtExist, Exceptions.TheParameterDoesNOtExist {

    }

    @Override
    public void removePromoCode(String promoCodeId, String token) throws Exceptions.TheParameterDoesNOtExist {

    }
}
