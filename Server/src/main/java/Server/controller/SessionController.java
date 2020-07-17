package Server.controller;

import com.fasterxml.jackson.annotation.*;
import exception.InvalidTokenException;
import exception.NotLoggedINException;
import model.enums.Role;
import model.Session;
import model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import repository.RepositoryContainer;

import javax.servlet.http.HttpSession;
import javax.swing.*;

@RestController
public class SessionController {
    public SessionController() {

    }

    @GetMapping("/session/hasUserLoggedIn")//TODO handle exception
    public Boolean isUserLoggedIn(@RequestBody String token) throws InvalidTokenException {
        Session session = Session.getSession(token);
        if (session.getLoggedInUser() == null)
            return false;
        return true;
    }

    @GetMapping("/session/getNewToken")
    public String createToken() {
        String token = Session.addSession();
        return token;
    }

    @GetMapping("/session/getUserRole")//TODO handle exception
    public Role getUserRole(@RequestBody String token) throws NotLoggedINException, InvalidTokenException {
        Session session = Session.getSession(token);
        User user = session.getLoggedInUser();
        if (user == null)
            throw new NotLoggedINException("you are not login to have a role.");
        return user.getRole();
    }

    @GetMapping("/session/getLoggedInUser")
    public User getUser(@RequestBody String token) throws InvalidTokenException {//TODO handle exception
        Session session = Session.getSession(token);
        User user = session.getLoggedInUser();
        return user;
    }
}
