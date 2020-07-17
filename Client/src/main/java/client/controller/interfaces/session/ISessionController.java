package client.controller.interfaces.session;

import exception.InvalidTokenException;
import exception.NotLoggedINException;
import model.User;
import model.enums.Role;

public interface ISessionController {
    boolean isUserLoggedIn(String token) throws InvalidTokenException;
    String createToken();
    Role getUserRole(String token) throws NotLoggedINException, InvalidTokenException;
    User getUser(String token) throws NotLoggedINException, InvalidTokenException;
}
