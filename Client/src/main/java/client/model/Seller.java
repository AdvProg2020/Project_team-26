package client.model;

import client.model.enums.Role;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class Seller extends User {

    public Seller() {
    }

    public Seller(String username, String password, String email, Role role) {
        super(username, password, email, role);
    }

    public void changeCompanyName(String companyName) {
        getDetails().put("Company Name", companyName);
    }
}
