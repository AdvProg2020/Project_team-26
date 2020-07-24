package Server.controller;

import com.fasterxml.jackson.annotation.*;
import com.google.gson.Gson;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import model.enums.Role;
import model.Session;
import model.User;
import org.springframework.web.bind.annotation.*;
import repository.RepositoryContainer;

import javax.swing.*;
import java.util.Map;

@RestController
public class SessionController {
    public SessionController() {

    }

    @PostMapping("/controller/method/session/hasUserLoggedIn")//TODO handle exception
    public Boolean isUserLoggedIn(@RequestBody Map info) throws InvalidTokenException {
        String token = (String) info.get("token");
        Session session = Session.getSession(token);
        if (session.getLoggedInUser() == null)
            return false;
        return true;
    }

    @GetMapping("/controller/method/session/getNewToken")
    public String createToken() {
        String token = Session.addSession();
        return token;
    }

    @GetMapping("/controller/method/session/getCommission/{token}")
    public Double getCommission(@PathVariable("token") String token) throws NoAccessException {
        return Session.getCommission();
    }

    @GetMapping("/controller/method/session/getMinCredit/{token}")
    public Long getMinCredit(@PathVariable("token") String token) throws NoAccessException {
        return Session.getMinCredit();
    }

    @PostMapping("/controller/method/session/setCommission")//TODO handle exception
    public void setCommission(@RequestBody Map info) throws NoAccessException, InvalidTokenException {
        String token = (String) info.get("token");
        Gson gson = new Gson();
        Double amount = gson.fromJson((String) info.get("amount"), Double.class);
        Session session = Session.getSession(token);
        User user = session.getLoggedInUser();
        if (user == null)
            throw new NoAccessException("you are not manager");
        if (user.getRole() != Role.ADMIN)
            throw new NoAccessException("you are not manager");
        Session.setCommission(amount);
    }

    @PostMapping("/controller/method/session/setMinCredit")//TODO handle exception
    public void setMinCredit(@RequestBody Map info) throws NoAccessException, InvalidTokenException {
        String token = (String) info.get("token");
        Gson gson = new Gson();
        Long amount = gson.fromJson((String) info.get("amount"), Long.class);
        Session session = Session.getSession(token);
        User user = session.getLoggedInUser();
        if (user == null)
            throw new NoAccessException("you are not manager");
        if (user.getRole() != Role.ADMIN)
            throw new NoAccessException("you are not manager");
        Session.setMinCredit(amount);
    }

    @PostMapping("/controller/method/session/getUserRole")//TODO handle exception
    public Role getUserRole(@RequestBody Map info) throws NotLoggedINException, InvalidTokenException {
        String token = (String) info.get("token");
        Session session = Session.getSession(token);
        User user = session.getLoggedInUser();
        if (user == null)
            throw new NotLoggedINException("you are not login to have a role.");
        return user.getRole();
    }

    @PostMapping("/controller/method/session/getLoggedInUser")
    public User getUser(@RequestBody Map info) throws InvalidTokenException {//TODO handle exception
        String token = (String) info.get("token");
        Session session = Session.getSession(token);
        User user = session.getLoggedInUser();
        return user;
    }
}
