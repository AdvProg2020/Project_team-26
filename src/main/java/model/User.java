package model;

import java.util.Map;

public class User implements Savable {

    private int id;
    private boolean isLoaded;
    private String username;
    private String password;
    private String email;
    private Role role;
    private Map<String, String> details;

    public User(int id) {
        this.id = id;
    }

    @Override
    public void load() {
        if(!isLoaded) {

            isLoaded = true;
        }
    }

    @Override
    public void save() {

    }
}
