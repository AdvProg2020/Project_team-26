package model;

import controller.account.Account;
import exception.NoAccessException;
import exception.NotEnoughCreditException;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import static javax.persistence.CascadeType.*;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "credit", nullable = false)
    private long credit;

    @ElementCollection
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    @CollectionTable(name = "user_details", joinColumns = @JoinColumn(name = "user_id"))
    private Map<String, String> details;

    public User() {
        details = new HashMap<>();
    }

    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        details = new HashMap<>();
    }

    public String getFullName() {
        return details.get("firstname") + " " + details.get("lastname");
    }

    public void changeFirstName(String name) {
        details.put("firstname", name);
    }

    public void changeLastName(String name) {
        details.put("lastname", name);
    }

    public void changeEmail(String Email) {
        this.email = Email;
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

        Map<String,String> details = new HashMap();
        details.put("Username",this.username);
        details.put("FirstName",details.get("firstname"));
        details.put("LastName",details.get("lastname"));
        details.put("Email",this.email);
        if(role == Role.SELLER) {
            details.put("Company Name",details.get("Company Name"));
        }
        return details;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void changeUsername(String username) {
        this.username = username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void changePassword(String oldPassword, String newPassword) throws NoAccessException {
        if (oldPassword.equals(this.password)) {
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
