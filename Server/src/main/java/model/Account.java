package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;
import model.enums.Role;

import java.io.IOException;
import java.util.Map;


@JsonDeserialize (using = AccountDeserializer.class)
public class Account {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private String companyName;
    private long credit;

    public Account() {

    }

    @JsonCreator
    public Account(@JsonProperty("username") String username,
                   @JsonProperty("password") String password,
                   @JsonProperty("email") String email,
                   @JsonProperty("firstName") String firstName,
                   @JsonProperty("lastName") String lastName,
                   @JsonProperty("role") Role role,
                   @JsonProperty("companyName") String companyName,
                   @JsonProperty("credit") long credit) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.companyName = companyName;
        this.credit = credit;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String setPassword(String password) {
        this.password = password;
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public User makeUser() {
        if (role == Role.CUSTOMER) {
            User customer = new Customer(username, password, email, role);
            customer.changeFirstName(firstName);
            customer.changeLastName(lastName);
            customer.setCredit(credit);
            return customer;
        } else if (role == Role.SELLER) {
            User seller = new Seller(username, password, email, role);
            seller.changeFirstName(firstName);
            seller.changeLastName(lastName);
            seller.setCredit(credit);
            seller.setCompanyName(companyName);
            return seller;
        } else {
            User admin = new Admin(username, password, email, role);
            admin.changeFirstName(firstName);
            admin.changeLastName(lastName);
            return admin;
        }
    }
}

class AccountDeserializer extends StdDeserializer<Account> {

    public AccountDeserializer() {
        super(Map.class);
    }

    @Override
    public Account deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String username = (node.get("username")).asText();
        String password = node.get("password").asText();
        String email = node.get("email").asText();
        String firstName = node.get("firstName").asText();
        String lastName = node.get("lastName").asText();
        Role role = Role.getRoleFromString(node.get("role").asText());
        String companyName = node.get("companyName").asText();
        long credit = (Long) ((LongNode) node.get("credit")).longValue();
        return new Account(username, password, email, firstName, lastName, role, companyName, credit);
    }
}
