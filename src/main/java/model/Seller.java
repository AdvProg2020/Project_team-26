package model;

import java.util.List;

public class Seller extends User {

    private List<Off> allOffs;

    public Seller(int id) {
        super(id);
    }

    public void setAllOffs(List<Off> allOffs) {
        this.allOffs = allOffs;
    }

    public List<Off> getAllOffs() {
        return allOffs;
    }
}
