package model;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class Seller extends User {

    // TODO: define off
//    private List<Off> allOffs;

    public Seller(String username, String password, String email, Role role) {
        super(username, password, email, role);
    }


//    public void setAllOffs(List<Off> allOffs) {
//        this.allOffs = allOffs;
//    }

//    public List<Off> getAllOffs() {
//        return allOffs;
//    }
}
