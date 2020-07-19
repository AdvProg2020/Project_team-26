package client.connectionController.interfaces.session;


import client.exception.InvalidTokenException;
import client.exception.NotLoggedINException;
import client.model.User;
import client.model.enums.Role;

public interface ISessionController {
    boolean isUserLoggedIn(String token) throws InvalidTokenException;
    String createToken();
    Role getUserRole(String token) throws NotLoggedINException, InvalidTokenException;
    User getUser(String token) throws NotLoggedINException, InvalidTokenException;
}
