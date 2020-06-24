package model;

import model.enums.Role;

import javax.persistence.Entity;

@Entity
public class Admin extends User {
    public Admin() {
    }

    public Admin(String username, String password, String email, Role role) {
        super(username, password, email, role);
    }
}
