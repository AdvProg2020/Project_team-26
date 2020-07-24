package client.connectionController.interfaces.session;


import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.exception.NotLoggedINException;
import client.model.User;
import client.model.enums.Role;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface ISessionController {
    boolean isUserLoggedIn(String token) throws InvalidTokenException;

    String createToken();

    Role getUserRole(String token) throws NotLoggedINException, InvalidTokenException;

    User getUser(String token) throws NotLoggedINException, InvalidTokenException;

    double getCommission(String token) throws NoAccessException, InvalidTokenException;

    long getMinCredit(String token) throws NoAccessException, InvalidTokenException;

    void setCommission(double amount, String token) throws NoAccessException, InvalidTokenException;

    void setMinCredit(long amount, String token) throws NoAccessException, InvalidTokenException;
}
