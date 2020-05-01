package controller.account;

import view.ViewManager;

public class AccountForView {
    private String password;
    private String role;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String token;
    private String id;

    public AccountForView() {
        this.username = new String();
        password = new String();
        this.role = new String();
        email = new String();
        firstName = new String();
        lastName = new String();
        token = new String();
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

    public String getRole() {
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

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
