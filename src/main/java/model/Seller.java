package model;

import java.util.List;

public class Seller extends User {

    private List<Off> allOffs;

    public Seller(String username, String password, String email, Role role) {
        super(username, password, email, role);
    }


    public void setAllOffs(List<Off> allOffs) {
        this.allOffs = allOffs;
    }

    public List<Off> getAllOffs() {
        return allOffs;
    }
}
