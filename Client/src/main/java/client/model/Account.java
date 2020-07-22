package client.model;

import client.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Map;

@JsonSerialize (using = AccountSerializer.class)
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
        this.username = new String();
        password = new String();
        email = new String();
        firstName = new String();
        lastName = new String();
    }

    public Account(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
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

    public Account(String username, String password, Role role, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public Account(String username, String password, Role role, String email, long credit) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
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

    public long getCredit() {
        return credit;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }
}

class AccountSerializer extends StdSerializer<Account> {

    public AccountSerializer() {
        super(Map.class, true);

    }

    @Override
    public void serialize(Account account, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("username", account.getUsername());
        jsonGenerator.writeStringField("password", account.getPassword());
        jsonGenerator.writeStringField("email", account.getEmail());
        jsonGenerator.writeStringField("firstName", account.getFirstName());
        jsonGenerator.writeStringField("lastName", account.getLastName());
        jsonGenerator.writeObjectField("role", account.getRole());
        jsonGenerator.writeStringField("companyName", account.getCompanyName());
        jsonGenerator.writeNumberField("credit", account.getCredit());
        jsonGenerator.writeEndObject();
    }
}
