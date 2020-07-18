package client.connectionController.discount;

import client.connectionController.interfaces.discount.IPromoController;
import client.exception.*;
import client.gui.Constants;
import client.model.*;
import client.model.enums.*;
import net.minidev.json.JSONObject;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;

public class PromoController implements IPromoController {

    public String getRandomPromoForUserSet(String token) throws InvalidTokenException {
        Session session = Session.getSession(token);
        if (session.getLoggedInUser() == null)
            return null;
        if (session.getLoggedInUser().getRole() != Role.CUSTOMER)
            return null;
        Pair<Boolean, String> promo = session.getPromoCodeForUser();
        if (promo.getKey() == false)
            return null;
        if (promo.getValue() == "")
            return null;
        session.setPromoCodeForUser(new Pair<>(false, ""));
        return promo.getValue();
    }

    public Promo getPromoCodeTemplateByCode(String codeId, String token) throws InvalidIdException, NotLoggedINException {
        Promo promo = promoRepository.getByCode(codeId);
        if (promo == null)
            throw new InvalidIdException("there is no promo by " + codeId);
        return promo;
    }

    public Promo getPromoCodeTemplateById(int codeId, String token) throws InvalidIdException, NotLoggedINException {
        Promo promo = promoRepository.getById(codeId);
        if (promo == null)
            throw new InvalidIdException("there is no promo by " + codeId);
        return promo;
    }

    public List<Promo> getAllPromoCodeForCustomer(String sortField, boolean isAscending, int startIndex, int endIndex, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException {
        Pageable page = createAPage(sortField, isAscending, startIndex, endIndex);
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("you are not logged in");
        } else if (user.getRole() == Role.SELLER) {
            throw new NoAccessException("only customer");
        }
        if (user.getRole() == Role.CUSTOMER) {
            return ((Customer) user).getAvailablePromos();
        } else {
            return promoRepository.getAll(page);
        }
    }

    public int createPromoCode(Promo promo, String token) throws NoAccessException, NotLoggedINException, ObjectAlreadyExistException, InvalidTokenException {
        checkAccessOfUser(token, "only the manager can create promo code");
        if (promoRepository.getByCode(promo.getPromoCode()) != null)
            throw new ObjectAlreadyExistException("the promo with code " + promo.getPromoCode() + " already exist", promo);
        promo.setMaxValidUse(25);
        promoRepository.save(promo);
        return promo.getId();
    }

    public void removePromoCode(int promoCodeId, String token) throws NotLoggedINException, NoAccessException, InvalidIdException, InvalidTokenException, NoObjectIdException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("promoCodeId", promoCodeId);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getOffControllerAddProductToOffAddress());
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }
    }

    public void addCustomer(int promoId, int customerId, String token) throws NoAccessException, InvalidIdException, ObjectAlreadyExistException, InvalidTokenException, NotLoggedINException, NotCustomerException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("promoId", promoId);
        jsonObject.put("customerId", customerId);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getOffControllerAddProductToOffAddress());
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }

    }

    public void removeCustomer(int promoId, int customerId, String token) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException, NotCustomerException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("promoId", promoId);
        jsonObject.put("customerId", customerId);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getOffControllerAddProductToOffAddress());
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }
    }

    public void setPercent(int promoId, double percent, String token) throws InvalidIdException, NoAccessException, InvalidTokenException, InvalidDiscountPercentException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("promoId", promoId);
        jsonObject.put("percent", percent);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getOffControllerAddProductToOffAddress());
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }
    }

    public void setMaxDiscount(int promoId, long maxDiscount, String token) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("promoId", promoId);
        jsonObject.put("maxDiscount", maxDiscount);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getOffControllerAddProductToOffAddress());
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }
    }


    public void setTime(int promoId, Date date, String type, String token) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("promoId", promoId);
        jsonObject.put("date", date);
        jsonObject.put("type", type);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getOffControllerAddProductToOffAddress());
        } catch (HttpClientErrorException e) {
            throw new InvalidTokenException("ksamd");
        }
    }
}
