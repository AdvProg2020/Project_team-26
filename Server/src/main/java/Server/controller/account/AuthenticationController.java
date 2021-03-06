package Server.controller.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import exception.*;
import model.*;
import model.enums.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import repository.PromoRepository;
import repository.RepositoryContainer;
import repository.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class AuthenticationController {

    UserRepository userRepository;
    PromoRepository promoRepository;
    Random randomNumberForPromo;
    private String captchaAnswer;

    public AuthenticationController() {
        this.userRepository = (UserRepository) RepositoryContainer.getInstance().getRepository("UserRepository");
        this.promoRepository = (PromoRepository) RepositoryContainer.getInstance().getRepository("PromoRepository");
        randomNumberForPromo = new Random();
    }

    @PostMapping("/controller/method/login")
    public void login(@RequestBody Map info) throws InvalidFormatException, PasswordIsWrongException, InvalidTokenException, InvalidAuthenticationException {
        String username = (String) info.get("username");
        String password = (String) info.get("password");
        String token = (String) info.get("token");
        if(!captchaAnswer.equals((String)info.get("captcha"))) {
            throw new InvalidAuthenticationException("Captcha is wrong.", "username");
        }
        Session userSession = Session.getSession(token);
        checkPasswordFormat(password);
        checkUsernameFormat(username);
        checkUsernameAndPassword(username, password);
        userSession.login(userRepository.getUserByUsername(username));
        if (userSession.getLoggedInUser() != null) {
            if (userSession.getLoggedInUser().getRole() == Role.CUSTOMER) {
                if (randomNumberForPromo.nextInt(200) < 50)
                    creatRandomPromo((Customer) userSession.getLoggedInUser(), userSession);
            } else {
                userSession.setRandomPromoCodeForUser(null);
            }
        }
    }

    private void creatRandomPromo(Customer customer, Session userSession) {
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
        int year = r.nextInt(2030 - 2022) + 2022;
        int month = r.nextInt(11 - 1) + 1;
        try {
            endDate = formatter.parse("20-" + month + "-" + year + " 8:00:00");
            promo.setEndDate(endDate);
            promo.setPromoCode(customer.getUsername().substring(0, (customer.getUsername().length() + 1) / 2) + new Date().getTime());
            if (promoRepository.getByCode(promo.getPromoCode()) == null) {
                promoRepository.save(promo);
                userSession.setRandomPromoCodeForUser(promo.getPromoCode());
            }
        } catch (ParseException e) {
            e.getStackTrace();
        }
    }

    @PostMapping("/controller/method/register")
    public void register(@RequestBody Map info) throws InvalidFormatException, NoAccessException, InvalidAuthenticationException, NoAccessException, InvalidTokenException, AlreadyLoggedInException {
        String token = (String) info.get("token");
        ObjectMapper objectMapper = new ObjectMapper();
        Account account = objectMapper.convertValue(info.get("account"), Account.class);
        Session userSession = Session.getSession(token);
        checkPasswordFormat(account.getPassword());
        checkUsernameFormat(account.getUsername());
        if (account.getRole() == Role.SUPPORT) {
            account.setEmail(account.getUsername() + "@gmail.com");
        }
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
                case SUPPORT:
                    registerSupport(account, token);
            }
        }
    }

    private void registerSupport(Account account, String token) throws InvalidTokenException, NoAccessException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null || user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            userRepository.save(account.makeUser());
        }
    }

    private void checkEmailAvailability(String email) throws InvalidAuthenticationException {
        User user = userRepository.getUserByEmail(email);
        if (user != null) {
            throw new InvalidAuthenticationException("Email is taken.", "Email");
        }
    }

    @PostMapping("/controller/method/logout")
    public void logout(@RequestBody Map info) throws NotLoggedINException, InvalidTokenException {
        String token = (String) info.get("token");
        Session userSession = Session.getSession(token);
        if (userSession.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in.");
        } else {
            userSession.logout();
        }
    }

    @GetMapping("/controller/method/get-captcha")
    public String getCaptcha() {
        int firstNumber = randomNumberForPromo.nextInt(100) + 1;
        int secondNumber = randomNumberForPromo.nextInt(100) + 1;
        captchaAnswer = Integer.toString(firstNumber + secondNumber);
        return "" + firstNumber + " + " + secondNumber;
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

    private void checkUsernameAvailability(String username) throws InvalidAuthenticationException {
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

    @GetMapping("/controller/method/do-we-have-a-manager")
    public boolean doWeHaveAManager() {
        return userRepository.doWeHaveAManager();
    }

}
