package controller.discount;

import controller.interfaces.discount.IPromoController;
import exception.*;
import model.*;
import model.repository.PromoRepository;
import model.repository.UserRepository;

import java.util.Date;
import java.util.List;

public class PromoController implements IPromoController {
    PromoRepository promoRepository;
    UserRepository userRepository;

    @Override
    public Promo getPromoCodeTemplateByCode(String codeId, String token) throws NoObjectWithIdException, NotLoggedINException {
        Promo promo = promoRepository.getByStringCode(codeId);
        if (promo == null)
            throw new NoObjectWithIdException("there is no promo by" + codeId);
        return promo;
    }


    @Override
    public Promo getPromoCodeTemplateById(int codeId, String token) throws NoObjectWithIdException, NotLoggedINException {
        Promo promo = promoRepository.getById(codeId);
        if (promo == null)
            throw new NoObjectWithIdException("there is no promo by" + codeId);
        return promo;
    }

    /**
     *
     */

    @Override
    public List<Promo> getAllPromoCode(String token) throws NotLoggedINException, NoAccessException {
        checkAccessOfUser(token, "only manager can see promos");
        Session session = Session.getSession(token);
        List<Promo> promos = session.getLoggedInUser().getPromoCodes();
        return promos;
    }

    @Override
    public int createPromoCode(String code, String token) throws NoAccessException, NotLoggedINException, ObjectAlreadyExistException {
        checkAccessOfUser(token, "only the manager can create promo code");
        Promo promo = promoRepository.getByStringCode(code);
        if (promo != null)
            throw new ObjectAlreadyExistException("the promo with code " + code + " already exist", promo);
        promo = new Promo(code);
        promoRepository.save(promo);
        return promo.getId();
    }

    private void checkAccessOfUser(String token, String message) throws NoAccessException {
        Session session = Session.getSession(token);
        if (!(session.getLoggedInUser().getRole() == Role.ADMIN))
            throw new NoAccessException(message);

    }

    /**
     * 2 function below should be check
     */

    @Override
    public void removePromoCode(int promoCodeId, String token) throws NotLoggedINException, NoAccessException, NoObjectWithIdException {
        checkAccessOfUser(token, "only manager can remove the promo");
        Promo promo = promoRepository.getById(promoCodeId);
        if (promo == null)
            throw new NoObjectWithIdException("there is no promo by" + promoCodeId);
        removeThePromoFromUsers(promo);
        promoRepository.delete(promoCodeId);
    }

    private void removeThePromoFromUsers(Promo promo) {
        List<Customer> customers = promo.getCustomers();
        for (Customer customer : customers) {
            customer.getPromoCodes().remove(promo);
            userRepository.save(customer);
        }
    }

    @Override
    public void addCustomer(int promoId, int customerId, int numberOfUse, String token) throws NoAccessException, NoObjectWithIdException {
        checkAccessOfUser(token, "only the manager can add customer");
        Promo promo = getPromoByIdWithCheck(promoId);
        Customer customer = (Customer) userRepository.getById(customerId);
        if (customer == null)
            throw new NoObjectWithIdException("no customer exist By " + customerId + " id");
        promo.getCustomers().add(customer);
        promoRepository.save(promo);
    }

    @Override
    public void removeCustomer(int promoId, int customerId, int numberOfUse, String token) throws NoAccessException, NoObjectWithIdException {
        checkAccessOfUser(token, "only the manager can add customer");
        Promo promo = getPromoByIdWithCheck(promoId);
        Customer customer = (Customer) userRepository.getById(customerId);
        if (customer == null)
            throw new NoObjectWithIdException("no customer exist By " + customerId + " id");
        List<Customer> promos = promo.getCustomers();
        if (!promos.contains(customer))
            throw new NoObjectWithIdException("the promo doesnt contain " + customerId + " id");
        promos.remove(customer);
        promoRepository.save(promo);
    }

    @Override
    public void setPercent(int promoId, double percent, String token) throws NoObjectWithIdException, NoAccessException, InvalidFormatException {
        checkAccessOfUser(token, "only manager can set percent");
        Promo promo = getPromoByIdWithCheck(promoId);
        if (percent > 100.0)
            throw new InvalidFormatException("the percent cant exceed 100%");
        promo.setPercent(percent);
        promoRepository.save(promo);
    }

    @Override
    public void setMaxDiscount(int promoId, long maxDiscount, String token) throws NoAccessException, NoObjectWithIdException {
        checkAccessOfUser(token, "only manager can set percent");
        Promo promo = getPromoByIdWithCheck(promoId);
        promo.setMaxDiscount(maxDiscount);
        promoRepository.save(promo);
    }

    private Promo getPromoByIdWithCheck(int id) throws NoObjectWithIdException {
        Promo promo = (Promo) promoRepository.getById(id);
        if (promo == null)
            throw new NoObjectWithIdException("no promo exist");
        return promo;
    }

    @Override
    public void setTime(int promoId, Date date, String type, String token) throws NoAccessException, NoObjectWithIdException {
        checkAccessOfUser(token, "only manager can " + type + " date");
        Promo promo = getPromoByIdWithCheck(promoId);
        if (type.equals("start")) {//ToDo

        } else {

        }

    }


}
