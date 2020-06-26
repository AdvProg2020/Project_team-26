package controller.account;

import controller.interfaces.account.IAuthenticationController;
import exception.*;
import model.*;
import model.enums.Role;
import repository.PromoRepository;
import repository.RepositoryContainer;
import repository.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class AuthenticationController implements IAuthenticationController {

    UserRepository userRepository;
    PromoRepository promoRepository;

    public AuthenticationController(RepositoryContainer repositoryContainer) {
        this.userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
        this.promoRepository = (PromoRepository) repositoryContainer.getRepository("PromoRepository");
    }

    public void login(String username, String password, String token) throws InvalidFormatException, PasswordIsWrongException, InvalidTokenException, InvalidAuthenticationException {
        Session userSession = Session.getSession(token);
        checkPasswordFormat(password);
        checkUsernameFormat(username);
        checkUsernameAndPassword(username, password);
        userSession.login(userRepository.getUserByUsername(username));
        if (userSession.getLoggedInUser().getRole() == Role.CUSTOMER) {
            /*Random r = new Random();
            if (r.nextInt(100) < 60)*/
            creatRandomPromo((Customer) userSession.getLoggedInUser(), token);
        }
    }

    private void creatRandomPromo(Customer customer, String token) {
        Promo promo = new Promo();
        promo.getCustomers().add(customer);
        promo.setMaxValidUse(1);
        promo.setMaxDiscount(50000);
        promo.setPercent(10);
        Date startDate = new Date();
        promo.setStartDate(startDate);
        Date endDate;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Random r = new Random();
        int year = r.nextInt(2030 - 2022) + 2020;
        int month = r.nextInt(11 - 1) + 1;
        try {
            endDate = formatter.parse("20-" + month + "-" + year + " 8:00:00");
            promo.setEndDate(endDate);
            promo.setPromoCode(customer.getUsername() + new Date().getTime());
            if (promoRepository.getByCode(promo.getPromoCode()) == null)
                promoRepository.save(promo);
        } catch (ParseException e) {
        }
    }

    public void register(Account account, String token) throws InvalidFormatException, NoAccessException, InvalidAuthenticationException, NoAccessException, InvalidTokenException, AlreadyLoggedInException {
        Session userSession = Session.getSession(token);
        checkPasswordFormat(account.getPassword());
        checkUsernameFormat(account.getUsername());
        checkEmailFormat(account.getEmail());
        checkEmailAvailability(account.getEmail());
        checkUsernameAvailability(account.getUsername());
        if (userSession.getLoggedInUser() != null && userSession.getLoggedInUser().getRole() != Role.ADMIN) {
            throw new AlreadyLoggedInException("You are logged in");
        } else {
            switch (account.getRole()) {
                case CUSTOMER:
                    registerCustomer(account, token);
                    break;
                case SELLER:
                    registerSeller(account, token);
                    break;
                case ADMIN:
                    registerAdmin(account, token);
            }
        }
    }

    private void checkEmailAvailability(String email) throws InvalidAuthenticationException {
        User user = userRepository.getUserByEmail(email);
        if(user != null) {
            throw new InvalidAuthenticationException("Email is taken.", "Email");
        }
    }

    public void logout(String token) throws NotLoggedINException, InvalidTokenException {
        Session userSession = Session.getSession(token);
        if (userSession.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in.");
        } else {
            userSession.logout();
        }
    }

    private void checkPasswordFormat(String password) throws InvalidFormatException {
        if (!password.matches("^[^\\s]+$")) {
            throw new InvalidFormatException("Password format is incorrect.", "Password");
        }
    }

    private void checkUsernameFormat(String username) throws InvalidFormatException {
        if (!username.matches("^[^\\s]+$")) {
            throw new InvalidFormatException("Username format is incorrect.", "Username");
        }
    }

    private void checkEmailFormat(String Email) throws InvalidFormatException {
        if (Email == null) {
            throw new InvalidFormatException("email should not be empty", "Email");
        }
        if (!Email.matches("^\\S+@\\S+.(?i)com(?-i)")) {
            throw new InvalidFormatException("Email format is incorrect.", "Email");
        }
    }

    public void checkUsernameAvailability(String username) throws InvalidAuthenticationException {
        if (userRepository.getUserByUsername(username) != null) {
            throw new InvalidAuthenticationException("Username is already taken.", "Username");
        }
    }

    private User createNewUser(Account account) {
        return account.makeUser();
    }

    private void checkUsernameAndPassword(String username, String password) throws InvalidAuthenticationException, PasswordIsWrongException {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new InvalidAuthenticationException("Username is invalid.", "Username");
        }
        if (!user.checkPassword(password)) {
            throw new InvalidAuthenticationException("Password is wrong", "Password");
        }
    }

    private void registerCustomer(Account account, String token) {
        userRepository.save(createNewUser(account));
    }

    private void registerSeller(Account account, String token) {
        userRepository.save(createNewUser(account));
    }

    private void registerAdmin(Account account, String token) throws NoAccessException, InvalidTokenException {
        Session userSession = Session.getSession(token);
        if (userSession.getLoggedInUser() == null) {
            if (userRepository.doWeHaveAManager()) {
                throw new NoAccessException("You are not allowed to do that.");
            } else {
                userRepository.save(account.makeUser());
            }
        } else {
            if (userSession.getLoggedInUser().getRole() != Role.ADMIN) {
                throw new NoAccessException("You are not allowed to do that.");
            } else {
                userRepository.save(account.makeUser());
            }
        }
    }

    public boolean doWeHaveAManager() {
        return userRepository.doWeHaveAManager();
    }

}
