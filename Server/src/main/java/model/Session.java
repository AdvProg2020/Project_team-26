package model;

import exception.InvalidTokenException;
import model.enums.Role;
import repository.UserRepository;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Session {

    private static Map<String, Session> sessionList = new HashMap<>();
    private static int lastToken = 0;
    private static long minCredit = 1000;
    private static double commission = 5;

    public static long getMinCredit() {
        return minCredit;
    }

    public static void setMinCredit(long minCredit) {
        Session.minCredit = minCredit;
    }

    public static double getCommission() {
        return commission;
    }

    public static void setCommission(double commission) {
        Session.commission = commission;
    }

    public static Session getSession(String token) throws InvalidTokenException {
        if (!sessionList.containsKey(token)) {
            throw new InvalidTokenException("You're token is invalid. Get a new token.");
        }
        return sessionList.get(token);
    }

    public static String addSession() {
        String token = toHexString(getSHA("some random string to change as a salt " + new Date().getTime()));

        sessionList.put(token, new Session());
        return token;
    }

    private static byte[] getSHA(String input)
    {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
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
    private String bankToken = "wrong";

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

    public String getBankToken() {
        return bankToken;
    }

    public void setBankToken(String bankToken) {
        this.bankToken = bankToken;
    }
}
