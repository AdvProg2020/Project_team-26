package client.connectionController.account;



import client.model.*;
import client.model.enums.Role;

public class Account {
    private String password;
    private Role role;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String id;
    private String companyName;
    private long credit;

    public Account() {
        this.username = new String();
        password = new String();
        email = new String();
        firstName = new String();
        lastName = new String();
    }

    public Account(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Account(String username, String password, Role role, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public Account(String username, String password, Role role, String email, long credit) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
