package model;

import java.util.HashMap;
import java.util.Map;

public class Session {

    private static Map<String, Session> sessionList = new HashMap<>();

    public static Session getSession(String token) {
        return sessionList.get(token);
    }

    public static String addSession() {
        String token = "";

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
}
