package model;

import exception.InvalidTokenException;

import java.util.HashMap;
import java.util.Map;

public class Session {

    private static Map<String, Session> sessionList = new HashMap<>();

    public static Session getSession(String token) throws InvalidTokenException {
        if(!sessionList.containsKey(token)) {
            throw new InvalidTokenException("You're token is invalid. Get a new token.");
        }
        return sessionList.get(token);
    }

    public static String addSession() {
        String token = "a";

        sessionList.put(token, new Session());
        return token;
    }

    private int id;
    private String token;
    private User loggedInUser;
    private Cart cart;

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
