package Server.controller;

import com.fasterxml.jackson.annotation.*;
import exception.InvalidTokenException;
import exception.NotLoggedINException;
import model.enums.Role;
import model.Session;
import model.User;
import org.springframework.web.bind.annotation.*;
import repository.RepositoryContainer;

import javax.servlet.http.HttpSession;
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
