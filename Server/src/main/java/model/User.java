package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import exception.NoAccessException;
import exception.NotEnoughCreditException;
import model.enums.Role;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true)
    private int id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @JsonProperty("role")
    private Role role;

    @Lob
    @Column(name = "image", columnDefinition = "mediumblob")
    private byte[] image;

    @Column(name = "credit", nullable = false)
    private long credit;

    @ElementCollection(fetch = FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @MapKeyColumn(name = "`key`")
    @Column(name = "value")
    @CollectionTable(name = "user_details", joinColumns = @JoinColumn(name = "user_id"))
    private Map<String, String> details;

    public User() {
        details = new HashMap<>();
    }

    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        details = new HashMap<>();
    }

    public String getFullName() {
        return details.get("firstname") + " " + details.get("lastname");
    }

    public void changeFirstName(String name) {
        details.put("firstname", name);
    }

    public void changeLastName(String name) {
        details.put("lastname", name);
    }

    public void changeEmail(String Email) {
        this.email = Email;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String Email) {
        this.email = Email;
    }

    public Role getRole() {
        return role;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFirstName() {
        return details.get("firstname");
    }

    public String getLastName() {
        return details.get("lastname");
    }

    public String getCompanyName() {
        if (role == Role.SELLER) {
            return details.get("Company Name");
        }
        return null;
    }

    public Map<String, String> getDetails() {
        Map<String, String> essentialDetails = new HashMap();
        essentialDetails.put("Username", this.username);
        essentialDetails.put("FirstName", details.get("firstname"));
        essentialDetails.put("LastName", details.get("lastname"));
        essentialDetails.put("Email", this.email);
        if (role == Role.SELLER) {
            essentialDetails.put("Company Name", details.get("Company Name"));
        }
        return essentialDetails;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void changeUsername(String username) {
        this.username = username;
    }

    public boolean checkPassword(String password) {
        return toHexString(getSHA(password)).equals(this.password);
    }

    public void changePassword(String oldPassword, String newPassword) throws NoAccessException {
        if (checkPassword(oldPassword)) {
            changePassword(newPassword);
        } else {
            throw new NoAccessException("You are not allowed to do that.");
        }
    }

    public void changePassword(String newPassword) {
        this.password = toHexString(getSHA(newPassword));
    }

    private byte[] getSHA(String input)
    {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public long getCredit() {
        return credit;
    }

    public void setCompanyName(String companyName) {
        details.put("Company Name", companyName);
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }

    public void pay(long amount) throws NotEnoughCreditException {
        if (amount > credit) {
            throw new NotEnoughCreditException("You don't have enough creadit to pay " + amount, credit);
        }

        credit -= amount;
    }

    public void changeCredit(long newCredit) {
        this.credit = newCredit;
    }

    @Override
    public String toString() {
        return username;
    }
}
