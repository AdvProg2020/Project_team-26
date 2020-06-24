package controller;

import controller.interfaces.session.ISessionController;
import exception.InvalidTokenException;
import exception.NotLoggedINException;
import model.enums.Role;
import model.Session;
import model.User;
import repository.RepositoryContainer;

public class SessionController implements ISessionController {
    public SessionController(RepositoryContainer repositoryContainer) {

    }

    @Override
    public boolean isUserLoggedIn(String token) throws InvalidTokenException {
        Session session = Session.getSession(token);
        if (session.getLoggedInUser() == null)
            return false;
        return true;
    }

    @Override
    public String createToken() {
        return Session.addSession();
    }

    @Override
    public Role getUserRole(String token) throws NotLoggedINException, InvalidTokenException {
        Session session = Session.getSession(token);
        User user = session.getLoggedInUser();
        if (user == null)
            throw new NotLoggedINException("you are not login to have a role.");
        return user.getRole();
    }

    @Override
    public User getUser(String token) throws InvalidTokenException {
        Session session = Session.getSession(token);
        User user = session.getLoggedInUser();
        return user;
    }
}
