package controller.discount;

import controller.Exceptions;
import controller.interfaces.discount.IPromoController;
import exception.*;
import model.Customer;
import model.Promo;
import model.Role;
import model.Session;
import model.repository.PromoRepository;
import model.repository.UserRepository;

import java.util.ArrayList;
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

    @Override
    public List<Promo> getAllPromoCode(String token) throws NotLoggedINException {
        Session session = Session.getSession(token);
        List<Promo> promos = session.getLoggedInUser().getPromoCodes();
        return promos;
    }

    @Override
    public int createPromoCode(String code, String token) throws NoAccessException, NotLoggedINException, ObjectAlreadyExistException {
        Session session = Session.getSession(token);
        if (session.getLoggedInUser().getRole() != Role.ADMIN)
            throw new NoAccessException("only the manager can create promo code");
        Promo promo = promoRepository.getByStringCode(code);
        if (promo != null)
            throw new ObjectAlreadyExistException("the promo with code " + code + " already exist", promo);
        promo = new Promo(code);
        promoRepository.save(promo);
        return promo.getId();
    }

    @Override
    public void editPromoCode(int oldPromoCodeId, String field, String replacement, String token) throws NoObjectWithIdException, NoSuchField {

    }

    @Override
    public void removePromoCode(int promoCodeId, String token) throws NotLoggedINException, NoAccessException, NoObjectWithIdException {
        Session session = Session.getSession(token);
        if (session.getLoggedInUser().getRole() != Role.ADMIN)
            throw new NoAccessException("only manager can remove the promo");
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
    public void addCustomer(int promoId, String CustomerUsername, int numberOfUse, String token) throws NoAccessException, NoObjectWithIdException {


    }

    @Override
    public void addTimeStartAndEnd(int promoId, Date startDate, Date endDate, String token) throws NoAccessException, NoObjectWithIdException {

    }
}
