package model;

import controller.account.Account;
import exception.NotEnoughCreditException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id", unique = true)
    private int id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "credit", nullable = false)
    private long credit;

    //TODO: define relations
    //private Map<String, String> details;


    public User() {
    }

    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public String getFullName() {
        return "";//TODO: details.get("firstname") + details.get("lastname");
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

    public Role getRole() {
        return role;
    }

    //TODO: redefine functions
//    public Map<String, String> getDetails() {
//        return details;
//    }
//

    public void setId(int id) {
        this.id = id;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
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
