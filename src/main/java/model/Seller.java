package model;

import java.util.ArrayList;
import java.util.List;

public class Seller extends User {

    private List<Off> allOffs;
    private String companyName;

    public Seller(String username, String password, String email, Role role) {
        super(username, password, email, role);
        allOffs = new ArrayList<>();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setAllOffs(List<Off> allOffs) {
        this.allOffs = allOffs;
    }

    public List<Off> getAllOffs() {
        return allOffs;
    }
}
