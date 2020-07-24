package model;

import exception.InvalidTokenException;
import model.enums.Role;
import repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class Session {

    private static Map<String, Session> sessionList = new HashMap<>();
    private static int lastToken = 0;

    public static Session getSession(String token) throws InvalidTokenException {
        if(!sessionList.containsKey(token)) {
            throw new InvalidTokenException("You're token is invalid. Get a new token.");
        }
        return sessionList.get(token);
    }

    public static String addSession() {
        lastToken++;
        String token = "a" + lastToken;

        sessionList.put(token, new Session());
        return token;
    }

    public static void initializeFake(UserRepository userRepository) {
        Session session1 = new Session();
        session1.loggedInUser = userRepository.getById(1);

        Session session2 = new Session();
        session2.loggedInUser = userRepository.getById(3);

        Session session3 = new Session();
        session3.loggedInUser = userRepository.getById(6);

        Session session4 = new Session();

        Session.sessionList.put("admin", session1);
        Session.sessionList.put("seller", session2);
        Session.sessionList.put("customer", session3);
        Session.sessionList.put("notloggedin", session4);
    }

    private int id;
    private String token;
    private User loggedInUser;
    private Cart cart;
    private String randomPromoCodeForUser;

    private Session() {
        cart = new Cart();
    }

    public static Map<String, Session> getSessionList() {
        return sessionList;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void logout() {
        this.loggedInUser = null;
    }

    public void login(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public String getRandomPromoCodeForUser() {
        return randomPromoCodeForUser;
    }

    public void setRandomPromoCodeForUser(String randomPromoCodeForUser) {
        this.randomPromoCodeForUser = randomPromoCodeForUser;
    }

    public Cart getCart() {
        return cart;
    }

    public boolean isUserAdmin() {
        if (loggedInUser.getRole() == Role.ADMIN)
            return true;
        return false;
    }

    public boolean isUserSeller() {
        if (loggedInUser.getRole() == Role.SELLER)
            return true;
        return false;
    }

    public boolean isUserCustomer() {
        if (loggedInUser.getRole() == Role.CUSTOMER)
            return true;
        return false;
    }

    public boolean IsUserLoggedIn() {
        if (loggedInUser == null)
            return false;
        return true;
    }
}
