package model;

import controller.account.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private Role role;
    private Map<String, String> details;
    private List<Promo> promoCodes;

    public User(int id) {
        this.id = id;
        promoCodes = new ArrayList<Promo>();
    }

    public User (Account account) {
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.password = account.getPassword();
        this.email = account.getEmail();
        this.role = getRole();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}
