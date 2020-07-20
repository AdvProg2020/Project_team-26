package client.model;

import client.model.enums.Role;

public class Customer extends User {

    public Customer() {
    }

    public Customer(String username, String password, String email, Role role) {
        super(username, password, email, role);
    }
}
