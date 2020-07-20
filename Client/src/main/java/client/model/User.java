package client.model;

import client.exception.*;
import client.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private Role role;
    private byte[] image;
    private long credit;
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

    @JsonCreator
    public User(@JsonProperty("username") String username,
                @JsonProperty("password") String password,
                @JsonProperty("email") String email,
                @JsonProperty("role") Role role,
                @JsonProperty("details") Map<String, String> details) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.details = details;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFirstName() {
        return details.get("firstname");
    }

    public String getLastName() {
        return details.get("lastname");
    }

    public String getCompanyName() {
        if (role == Role.SELLER) {
            return details.get("Company Name");
        }
        return null;
    }

    public Map<String, String> getDetails() {
        Map<String, String> essentialDetails = new HashMap();
        essentialDetails.put("Username", this.username);
        essentialDetails.put("FirstName", details.get("firstname"));
        essentialDetails.put("LastName", details.get("lastname"));
        essentialDetails.put("Email", this.email);
        if (role == Role.SELLER) {
            essentialDetails.put("Company Name", details.get("Company Name"));
        }
        return essentialDetails;
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

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public long getCredit() {
        return credit;
    }

    public void setCompanyName(String companyName) {
        details.put("Company Name", companyName);
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }

    public void pay(long amount) throws NotEnoughCreditException {
        if (amount > credit) {
            throw new NotEnoughCreditException("You don't have enough creadit to pay " + amount, credit);
        }

        credit -= amount;
    }

    public void changeCredit(Long newCredit) {
        this.credit = newCredit;
    }

    @Override
    public String toString() {
        return username;
    }
}
