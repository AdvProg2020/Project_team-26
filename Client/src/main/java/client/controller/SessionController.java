package client.controller;

import exception.InvalidTokenException;
import exception.NotLoggedINException;
import model.Session;
import model.User;
import model.enums.Role;
import repository.RepositoryContainer;

public class SessionController {
    public SessionController(RepositoryContainer repositoryContainer) {

    }

    public Boolean isUserLoggedIn(String token) throws InvalidTokenException {
        Session session = Session.getSession(token);
        if (session.getLoggedInUser() == null)
            return false;
        return true;
    }


    public String createToken() {
        String token = Session.addSession();
        return token;
    }

    public Role getUserRole(String token) throws NotLoggedINException, InvalidTokenException {
        Session session = Session.getSession(token);
        User user = session.getLoggedInUser();
        if (user == null)
            throw new NotLoggedINException("you are not login to have a role.");
        return user.getRole();
    }

    public User getUser(String token) throws InvalidTokenException {
        Session session = Session.getSession(token);
        User user = session.getLoggedInUser();
        return user;
    }
}
