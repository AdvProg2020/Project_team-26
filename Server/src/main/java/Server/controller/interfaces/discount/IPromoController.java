package Server.controller.interfaces.discount;

import exception.*;
import model.*;

import java.util.*;

public interface IPromoController {

    Promo getPromoCodeTemplateByCode(String codeId, String token) throws InvalidIdException, NotLoggedINException;

    Promo getPromoCodeTemplateById(int codeId, String token) throws InvalidIdException, NotLoggedINException;

    List<Promo> getAllPromoCodeForCustomer(String sortField, boolean isAscending,int startIndex,int endIndex, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException;

    int createPromoCode(Promo promo, String token) throws NoAccessException, NotLoggedINException, ObjectAlreadyExistException, InvalidTokenException;

    void removePromoCode(int promoCodeId, String token) throws NoAccessException, NotLoggedINException, NoAccessException, InvalidIdException, InvalidTokenException, NoObjectIdException;

    void addCustomer(int promoId, int CustomerUserId, String token) throws NoAccessException, InvalidIdException, ObjectAlreadyExistException, InvalidTokenException, NotLoggedINException, NotCustomerException;

    void setTime(int promoId, Date date, String type, String token) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException;

    void removeCustomer(int promoId, int customerId,String token) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException, NotCustomerException;

    void setPercent(int promoId, double percent, String token) throws InvalidIdException, NoAccessException, InvalidFormatException, InvalidTokenException, InvalidDiscountPercentException, NotLoggedINException;

    void setMaxDiscount(int promoId, long maxDiscount, String token) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException;

}
