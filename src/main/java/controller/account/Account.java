package controller.account;

import model.*;
import view.ViewManager;

public class Account {
    private String password;
    private Role role;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String token;
    private String id;

    public Account() {
        this.username = new String();
        password = new String();
        email = new String();
        firstName = new String();
        lastName = new String();
        token = new String();
    }

    public Account(String username, String password,Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String setPassword(String password) {
        this.password = password;
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public Role getRole() {
        return role;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public User makeUser() {
        if ( this.getRole() == Role.ADMIN)
            return new Admin( this.getUsername(),  this.getPassword(),  this.getEmail(),  this.getRole());
        if ( this.getRole() == Role.SELLER)
            return new Seller( this.getUsername(),  this.getPassword(),  this.getEmail(),  this.getRole());
        return new Customer( this.getUsername(),  this.getPassword(),  this.getEmail(),  this.getRole());

    }

    public void setUsername(String username) {
        this.username = username;
    }

}
