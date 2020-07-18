package client.connectionController.discount;


import client.connectionController.interfaces.discount.IPromoController;
import client.exception.*;
import client.model.*;
import client.model.enums.Role;

import java.util.Date;
import java.util.List;

public class PromoController implements IPromoController {

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

    private void checkAccessOfUser(String token, String message) throws NoAccessException, InvalidTokenException, NotLoggedINException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not logged in.");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException(message);
        }
    }

    private Pageable createAPage(String sortField, boolean isAscending, int startIndex, int endIndex) {
        if (isAscending) {
            return new Pageable(startIndex, endIndex, sortField, Pageable.Direction.ASCENDING);
        } else {
            return new Pageable(startIndex, endIndex, sortField, Pageable.Direction.DESCENDING);
        }
    }

    /**
     * 2 function below should be checked
     */

    public void removePromoCode(int promoCodeId, String token) throws NotLoggedINException, NoAccessException, InvalidIdException, InvalidTokenException, NoObjectIdException {
        checkAccessOfUser(token, "only manager can remove the promo");
        Promo promo = promoRepository.getById(promoCodeId);
        if (promo == null)
            throw new InvalidIdException("there is no promo by " + promoCodeId);
        promoRepository.delete(promo);
    }

    public void addCustomer(int promoId, int customerId, String token) throws NoAccessException, InvalidIdException, ObjectAlreadyExistException, InvalidTokenException, NotLoggedINException, NotCustomerException {
        checkAccessOfUser(token, "only the manager can add customer");
        Promo promo = getPromoByIdWithCheck(promoId);
        User user = userRepository.getById(customerId);

        if (user == null) {
            throw new InvalidIdException("no customer exists By " + customerId + " id");
        } else if (user.getRole() != Role.CUSTOMER) {
            throw new NotCustomerException("Only Customers can have promo codes");
        } else if (promo.getCustomers().contains((Customer) user)) {
            throw new ObjectAlreadyExistException("the promo contain this customer", (Customer)
                    user);
        } else {
            promo.getCustomers().add((Customer) user);
            promoRepository.save(promo);
        }

    }

    public void removeCustomer(int promoId, int customerId, String token) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException, NotCustomerException {
        checkAccessOfUser(token, "only the manager can remove customer");
        Promo promo = getPromoByIdWithCheck(promoId);
        User user = userRepository.getById(customerId);
        if (user == null) {
            throw new InvalidIdException("no customer exist By " + customerId + " id");
        } else if (user.getRole() != Role.CUSTOMER) {
            throw new NotCustomerException("You must choose a customer");
        }
        Customer customer = (Customer) user;
        List<Customer> promos = promo.getCustomers();

        if (!promos.contains(customer))
            throw new InvalidIdException("the promo doesnt contain " + customerId + " id");

        promos.remove(customer);
        promoRepository.save(promo);
    }

    public void setPercent(int promoId, double percent, String token) throws InvalidIdException, NoAccessException, InvalidTokenException, InvalidDiscountPercentException, NotLoggedINException {
        checkAccessOfUser(token, "only manager can set percent");
        Promo promo = getPromoByIdWithCheck(promoId);
        if (percent > 100.0)
            throw new InvalidDiscountPercentException("the percent can't exceed 100%");
        promo.setPercent(percent);
        promoRepository.save(promo);
    }

    public void setMaxDiscount(int promoId, long maxDiscount, String token) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException {
        checkAccessOfUser(token, "only manager can set percent");
        Promo promo = getPromoByIdWithCheck(promoId);
        promo.setMaxDiscount(maxDiscount);
        promoRepository.save(promo);
    }

    private Promo getPromoByIdWithCheck(int id) throws InvalidIdException {
        Promo promo = (Promo) promoRepository.getById(id);
        if (promo == null)
            throw new InvalidIdException("no promo exist");
        return promo;
    }

    public void setTime(int promoId, Date date, String type, String token) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException {
        checkAccessOfUser(token, "only manager can " + type + " date");
        Promo promo = getPromoByIdWithCheck(promoId);
        if (type.equals("start")) {
            promo.setStartDate(date);
        } else {
            promo.setEndDate(date);
        }
        promoRepository.save(promo);
    }

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
}
