package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.enums.Role;

public class Account {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private String companyName;
    private long credit;

    public Account(@JsonProperty("username") String username,
                   @JsonProperty("password") String password,
                   @JsonProperty("email") String email,
                   @JsonProperty("firstName") String firstName,
                   @JsonProperty("lastName") String lastName,
                   @JsonProperty("role") Role role,
                   @JsonProperty("companyName") String companyName,
                   @JsonProperty("credit") long credit) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.companyName = companyName;
        this.credit = credit;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String setPassword(String password) {
        this.password = password;
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public User makeUser() {
        if (role == Role.CUSTOMER) {
            User customer = new Customer(username, password, email, role);
            customer.changeFirstName(firstName);
            customer.changeLastName(lastName);
            customer.setCredit(credit);
            return customer;
        } else if (role == Role.SELLER) {
            User seller = new Seller(username, password, email, role);
            seller.changeFirstName(firstName);
            seller.changeLastName(lastName);
            seller.setCredit(credit);
            seller.setCompanyName(companyName);
            return seller;
        } else {
            User admin = new Admin(username, password, email, role);
            admin.changeFirstName(firstName);
            admin.changeLastName(lastName);
            return admin;
        }
    }


}
