package model;

import controller.account.Account;
import exception.NoAccessException;
import exception.NotEnoughCreditException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private Role role;
    private long credit;
    private Map<String, String> details;
    private List<Promo> promoCodes;

    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public String getFullName() {
        return details.get("firstname") + details.get("lastname");
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String Email) {
        this.email = Email;
    }

    public Role getRole() {
        return role;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public List<Promo> getPromoCodes() {
        return promoCodes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void changePassword(String oldPassword, String newPassword) throws NoAccessException {
        if(oldPassword.equals(this.password)) {
            this.password = newPassword;
        } else {
            throw new NoAccessException("You are not allowed to do that.");
        }
    }

    public long getCredit() {
        return credit;
    }

    public void pay(long amount) throws NotEnoughCreditException {
        if (amount > credit) {
            throw new NotEnoughCreditException("You don't have enough creadit to pay " + amount, credit);
        }

        credit -= amount;
    }
}
