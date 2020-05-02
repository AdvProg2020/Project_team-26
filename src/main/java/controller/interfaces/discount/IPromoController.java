package controller.interfaces.discount;

import exception.*;
import model.*;

import java.util.*;

public interface IPromoController {

    Promo getPromoCodeTemplateByCode(String codeId, String token) throws NoObjectWithIdException, NotLoggedINException;

    Promo getPromoCodeTemplateById(int codeId, String token) throws NoObjectWithIdException, NotLoggedINException;

    List<Promo> getAllPromoCode(String token) throws NotLoggedINException;

    int createPromoCode(String code, String token) throws NoAccessException, NotLoggedINException, ObjectAlreadyExistException;

    void removePromoCode(int promoCodeId, String token) throws NoAccessException, NotLoggedINException, NoAccessException, NoObjectWithIdException;

    void addCustomer(int promoId, int CustomerUserId, int numberOfUse, String token) throws NoAccessException, NoObjectWithIdException;

    void setTime(int promoId, Date date, String type, String token) throws NoAccessException, NoObjectWithIdException;

    void removeCustomer(int promoId, int customerId, int numberOfUse, String token) throws NoAccessException, NoObjectWithIdException;

    void setPercent(int promoId, double percent, String token) throws NoObjectWithIdException, NoAccessException , InvalidFormatException;

    void setMaxDiscount(int promoId, long maxDiscount , String token) throws NoAccessException,NoObjectWithIdException;

}
