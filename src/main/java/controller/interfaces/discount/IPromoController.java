package controller.interfaces.discount;

import exception.*;
import model.*;

import java.util.*;

public interface IPromoController {

    Promo getPromoCodeTemplateByCode(String codeId, String token) throws NoObjectWithIdException, NotLoggedINException;

    Promo getPromoCodeTemplateById(int codeId, String token) throws NoObjectWithIdException, NotLoggedINException;

    List<Promo> getAllPromoCode(String token) throws NotLoggedINException;

    int createPromoCode(String code, String token) throws NoAccessException, NotLoggedINException, ObjectAlreadyExistException;

    void editPromoCode(int oldPromoCodeId, String field, String replacement, String token) throws NoObjectWithIdException, NoSuchField;

    void removePromoCode(int promoCodeId, String token) throws NoAccessException, NotLoggedINException, NoAccessException, NoObjectWithIdException;

    void addCustomer(int promoId, int CustomerUserId, int numberOfUse, String token) throws NoAccessException, NoObjectWithIdException;

    void addTimeStartAndEnd(int promoId, Date startDate, Date endDate, String token) throws NoAccessException, NoObjectWithIdException;


}
