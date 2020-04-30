package controller.interfaces.discount;

import controller.Exceptions;
import controller.discount.PromoDetailsController;

import java.util.ArrayList;

public interface IPromoController {

    PromoDetailsController getPromoCodeTemplate(String codeId, String token) throws Exceptions.TheParameterDoesNOtExist;

    PromoDetailsController[] getAllPromoCode(String token);

    void createPromoCode(String promoCode, String token);

    void editPromoCode(String oldPromoCodeId, ArrayList<String> fieldAndChanges, String token) throws Exceptions.TheParameterDoesNOtExist, Exceptions.TheParameterDoesNOtExist;

    void removePromoCode(String promoCodeId, String token) throws Exceptions.TheParameterDoesNOtExist;
}
