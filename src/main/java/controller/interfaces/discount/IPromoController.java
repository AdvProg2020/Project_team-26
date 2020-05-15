package controller.interfaces.discount;

import exception.*;
import model.*;

import java.util.*;

public interface IPromoController {

    Promo getPromoCodeTemplateByCode(String codeId, String token) throws InvalidIdException, NotLoggedINException;

    Promo getPromoCodeTemplateById(int codeId, String token) throws InvalidIdException, NotLoggedINException;

    List<Promo> getAllPromoCodeForCustomer(Map<String, String> filter, String sortField, boolean isAcsending, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException;

    //List<Promo> getAllPromoCode(String token) throws NotLoggedINException, NoAccessException, InvalidTokenException;

    int createPromoCode(Promo promo, String token) throws NoAccessException, NotLoggedINException, ObjectAlreadyExistException, InvalidTokenException;

    void removePromoCode(int promoCodeId, String token) throws NoAccessException, NotLoggedINException, NoAccessException, InvalidIdException, InvalidTokenException, NoObjectIdException;

    void addCustomer(int promoId, int CustomerUserId, int numberOfUse, String token) throws NoAccessException, InvalidIdException, ObjectAlreadyExistException, InvalidTokenException;

    void setTime(int promoId, Date date, String type, String token) throws NoAccessException, InvalidIdException, InvalidTokenException;

    void removeCustomer(int promoId, int customerId, int numberOfUse, String token) throws NoAccessException, InvalidIdException, InvalidTokenException;

    void setPercent(int promoId, double percent, String token) throws InvalidIdException, NoAccessException, InvalidFormatException, InvalidTokenException, InvalidDiscountPercentException;

    void setMaxDiscount(int promoId, long maxDiscount, String token) throws NoAccessException, InvalidIdException, InvalidTokenException;

}
