package Server.controller.discount;

import exception.*;
import javafx.util.Pair;
import model.*;
import model.enums.Role;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import repository.Pageable;
import repository.PromoRepository;
import repository.RepositoryContainer;
import repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class PromoController {
    PromoRepository promoRepository;
    UserRepository userRepository;

    public PromoController () {

    }
    public PromoController(RepositoryContainer repositoryContainer) {
        promoRepository = (PromoRepository) repositoryContainer.getRepository("PromoRepository");
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
    }
    @PostMapping("/controller/method/promo/get-promo-code-template-by-code")
    public Promo getPromoCodeTemplateByCode(@RequestBody Map info) throws InvalidIdException, NotLoggedINException {
        String codeId = (String) info.get("codeId");
        String token = (String) info.get("token");
        Promo promo = promoRepository.getByCode(codeId);
        if (promo == null)
            throw new InvalidIdException("there is no promo by " + codeId);
        return promo;
    }

    @PostMapping("/controller/method/promo/get-promo-code-template-by-id")
    public Promo getPromoCodeTemplateById(@RequestBody Map info) throws InvalidIdException, NotLoggedINException {
        int codeId = (Integer) info.get("codeId");
        String token = (String) info.get("token");
        Promo promo = promoRepository.getById(codeId);
        if (promo == null)
            throw new InvalidIdException("there is no promo by " + codeId);
        return promo;
    }

    @PostMapping("/controller/method/promo/get-all-promo-code-for-customer")
    public List<Promo> getAllPromoCodeForCustomer(@RequestBody Map info) throws NotLoggedINException, NoAccessException, InvalidTokenException {
        String sortField = (String) info.get("sortField");
        boolean isAscending = (Boolean) info.get("isAscending");
        int startIndex = (Integer) info.get("startIndex");
        int endIndex = (Integer) info.get("endIndex");
        String token = (String) info.get("token");
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
    @PostMapping("/controller/method/promo/create-promo-code")
    public int createPromoCode(@RequestBody Map info) throws NoAccessException, NotLoggedINException, ObjectAlreadyExistException, InvalidTokenException {
        Promo promo = (Promo) info.get("promo");
        String token = (String) info.get("token");
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

    @PostMapping("/controller/method/promo/remove-promo-code")
    public void removePromoCode(@RequestBody Map info) throws NotLoggedINException, NoAccessException, InvalidIdException, InvalidTokenException, NoObjectIdException {
        int promoCodeId = (Integer) info.get("promoCodeId");
        String token = (String) info.get("token");
        checkAccessOfUser(token, "only manager can remove the promo");
        Promo promo = promoRepository.getById(promoCodeId);
        if (promo == null)
            throw new InvalidIdException("there is no promo by " + promoCodeId);
        promoRepository.delete(promo);
    }

    @PostMapping("/controller/method/promo/add-customer")
    public void addCustomer(@RequestBody Map info) throws NoAccessException, InvalidIdException, ObjectAlreadyExistException, InvalidTokenException, NotLoggedINException, NotCustomerException {
        int promoId = (Integer) info.get("promoId");
        int customerId = (Integer) info.get("customerId");
        String token = (String) info.get("token");
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

    @PostMapping("/controller/method/promo/remove-customer")
    public void removeCustomer(@RequestBody Map info) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException, NotCustomerException {
        int promoId = (Integer) info.get("promoId");
        int customerId = (Integer) info.get("customerId");
        String token = (String) info.get("token");
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

    @PostMapping("/controller/method/promo/set-percent")
    public void setPercent(@RequestBody Map info) throws InvalidIdException, NoAccessException, InvalidTokenException, InvalidDiscountPercentException, NotLoggedINException {
        int promoId = (Integer) info.get("promoId");
        double percent = (Double) info.get("percent");
        String token = (String) info.get("token");
        checkAccessOfUser(token, "only manager can set percent");
        Promo promo = getPromoByIdWithCheck(promoId);
        if (percent > 100.0)
            throw new InvalidDiscountPercentException("the percent can't exceed 100%");
        promo.setPercent(percent);
        promoRepository.save(promo);
    }

    @PostMapping("/controller/method/promo/set-max-discount")
    public void setMaxDiscount(@RequestBody Map info) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException {
        int promoId = (Integer) info.get("promoId");
        long maxDiscount = (Integer) info.get("maxDiscount");
        String token = (String) info.get("token");
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

    @PostMapping("/controller/method/promo/set-time")
    public void setTime(@RequestBody Map info) throws NoAccessException, InvalidIdException, InvalidTokenException, NotLoggedINException {
        int promoId = (Integer) info.get("promoId");
        Date date = (Date) info.get("date");
        String type = (String) info.get("type");
        String token = (String) info.get("token");
        checkAccessOfUser(token, "only manager can " + type + " date");
        Promo promo = getPromoByIdWithCheck(promoId);
        if (type.equals("start")) {
            promo.setStartDate(date);
        } else {
            promo.setEndDate(date);
        }
        promoRepository.save(promo);
    }

    @PostMapping("/controller/method/promo/get-random-promo-for-user-set")
    public String getRandomPromoForUserSet(@RequestBody Map info) throws InvalidTokenException {
        String token = (String) info.get("token");
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
