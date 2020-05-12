package model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Seller extends User {

    @OneToMany(mappedBy = "seller")
    private List<Off> allOffs;

    public Seller() {
    }

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
