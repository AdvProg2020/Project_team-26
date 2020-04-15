package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private Role role;
    private Map<String, String> details;
    private List<Promo> promoCodes;

    public User(int id) {
        this.id = id;
        promoCodes = new ArrayList<Promo>();
    }
}
