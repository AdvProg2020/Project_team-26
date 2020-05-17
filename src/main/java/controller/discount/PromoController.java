package controller.discount;

import interfaces.discount.IPromoController;
import exception.*;
import model.*;
import repository.PromoRepository;
import repository.RepositoryContainer;
import repository.UserRepository;

import java.util.Date;
import java.util.List;

public class PromoController implements IPromoController {
    PromoRepository promoRepository;
    UserRepository userRepository;

    public PromoController(RepositoryContainer repositoryContainer) {
        promoRepository = (PromoRepository) repositoryContainer.getRepository("PromoRepository");
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
    }

    @Override
    public Promo getPromoCodeTemplateByCode(String codeId, String token) throws InvalidIdException, NotLoggedINException {
        Promo promo = promoRepository.getByCode(codeId);
        if (promo == null)
            throw new InvalidIdException("there is no promo by " + codeId);
        return promo;
    }


    @Override
    public Promo getPromoCodeTemplateById(int codeId, String token) throws InvalidIdException, NotLoggedINException {
        Promo promo = promoRepository.getById(codeId);
        if (promo == null)
            throw new InvalidIdException("there is no promo by " + codeId);
        return promo;
    }

    @Override
    public List<Promo> getAllPromoCodeForCustomer(String sortField, boolean isAscending, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("you are not logged in");
        } else if (user.getRole() == Role.SELLER) {
            throw new NoAccessException("only customer");
        }
        if (user.getRole() == Role.CUSTOMER) {
            return ((Customer) user).getAvailablePromos();
        } else {
            return promoRepository.getAllSorted(sortField,isAscending);
        }

    }

    @Override
    public int createPromoCode(Promo promo, String token) throws NoAccessException, NotLoggedINException, ObjectAlreadyExistException, InvalidTokenException {
        checkAccessOfUser(token, "only the manager can create promo code");
        if (promoRepository.getByCode(promo.getPromoCode()) != null)
            throw new ObjectAlreadyExistException("the promo with code " + promo.getPromoCode() + " already exist", promo);
        promoRepository.save(promo);
        return promo.getId();
    }

    private void checkAccessOfUser(String token, String message) throws NoAccessException, InvalidTokenException, NotLoggedINException {
        Session session = Session.getSession(token);

        if (session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in.");
        } else if (session.getLoggedInUser().getRole() != Role.ADMIN) {
            throw new NoAccessException(message);
        }
    }

    /**
     * 2 function below should be checked
     */

    @Override
    public void removePromoCode(int promoCodeId, String token) throws NotLoggedINException, NoAccessException, InvalidIdException, InvalidTokenException, NoObjectIdException {
        checkAccessOfUser(token, "only manager can remove the promo");
        Promo promo = promoRepository.getById(promoCodeId);
        if (promo == null)
            throw new InvalidIdException("there is no promo by " + promoCodeId);
        removeThePromoFromUsers(promo);
        promoRepository.delete(promoCodeId);
    }

    private void removeThePromoFromUsers(Promo promo) {
        List<Customer> customers = promo.getCustomers();
        for (Customer customer : customers) {
            customer.getAvailablePromos().remove(promo);
            userRepository.save(customer);
        }
    }

    @Override
    public void addCustomer(int promoId, int customerId, String token) throws NoAccessException, InvalidIdException, ObjectAlreadyExistException, InvalidTokenException, NotLoggedINException, NotCustomerException {
        checkAccessOfUser(token, "only the manager can add customer");
        Promo promo = getPromoByIdWithCheck(promoId);

        if (userRepository.getById(customerId) == null) {
            throw new InvalidIdException("no customer exists By " + customerId + " id");
        } else if (userRepository.getById(customerId).getRole() != Role.CUSTOMER) {
            throw new NotCustomerException("Only Customers can have promo codes");
        } else if (promo.getCustomers().contains((Customer) userRepository.getById(customerId))) {
            throw new ObjectAlreadyExistException("the promo contain this customer", (Customer)
                    userRepository.getById(customerId));
        } else {
            promo.getCustomers().add((Customer) userRepository.getById(customerId));
            promoRepository.save(promo);
        }

    }

    @Override
    public void removeCustomer(int promoId, int customerId, String token) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException, NotCustomerException {
        checkAccessOfUser(token, "only the manager can remove customer");
        Promo promo = getPromoByIdWithCheck(promoId);
        if (userRepository.getById(customerId) == null) {
            throw new InvalidIdException("no customer exist By " + customerId + " id");
        } else if (userRepository.getById(customerId).getRole() != Role.CUSTOMER) {
            throw new NotCustomerException("You must choose a customer");
        }
        Customer customer = (Customer) userRepository.getById(customerId);
        List<Customer> promos = promo.getCustomers();

        if (!promos.contains(customer))
            throw new InvalidIdException("the promo doesnt contain " + customerId + " id");

        promos.remove(customer);
        promoRepository.save(promo);
    }

    @Override
    public void setPercent(int promoId, double percent, String token) throws InvalidIdException, NoAccessException, InvalidTokenException, InvalidDiscountPercentException, NotLoggedINException {
        checkAccessOfUser(token, "only manager can set percent");
        Promo promo = getPromoByIdWithCheck(promoId);
        if (percent > 100.0)
            throw new InvalidDiscountPercentException("the percent can't exceed 100%");
        promo.setPercent(percent);
        promoRepository.save(promo);
    }

    @Override
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

    @Override
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
}
