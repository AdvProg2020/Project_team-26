package model;

import controller.account.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class User {

    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private Map<String, String> details;
    private List<Promo> promoCodes;

    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
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

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean checkPassword(String password) {
        if(this.password.equals(password)) {
            return true;
        }
        return false;
    }
}
