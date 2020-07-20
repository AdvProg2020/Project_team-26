package client.model;

import client.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Admin extends User {
    public Admin() {
    }

    @JsonCreator
    public Admin(@JsonProperty("username") String username,
                 @JsonProperty("password") String password,
                 @JsonProperty("email") String email,
                 @JsonProperty("role") Role role) {
        super(username, password, email, role);
    }
}
