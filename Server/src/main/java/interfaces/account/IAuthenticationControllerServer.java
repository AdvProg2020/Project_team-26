package interfaces.account;

import controller.account.Account;
import exception.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface IAuthenticationControllerServer {

    void login(String username, String password) throws PasswordIsWrongException,InvalidTokenException, InvalidFormatException, InvalidAuthenticationException;
    void register(Account account) throws NoAccessException, InvalidFormatException, InvalidTokenException, InvalidAuthenticationException, AlreadyLoggedInException;

    void logout() throws  InvalidTokenException, NotLoggedINException;
}
