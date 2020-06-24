package controller;

import controller.account.AuthenticationController;
import exception.*;
import model.enums.Role;
import model.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.RepositoryContainer;

public class SessionControllerTest {

    private RepositoryContainer repositoryContainer;
    private SessionController sessionController;
    private AuthenticationController authenticationController;
    private String token;


    @BeforeEach
    public void setup() {
        repositoryContainer = new RepositoryContainer("sql");
        sessionController = new SessionController(repositoryContainer);
        authenticationController = new AuthenticationController(repositoryContainer);
        token = Session.addSession();
    }


    @Test
    public void createTokenTest() {
        sessionController.createToken();
    }

    @Test
    public void getUser() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException {
        authenticationController.login("aria", "aria", token);
        Assertions.assertEquals(sessionController.getUser(token).getRole(), Role.ADMIN);
    }

    @Test
    public void isUserLoggedIn() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException {
        Assertions.assertEquals(sessionController.isUserLoggedIn(token), false);
        authenticationController.login("test1", "test1", token);
        Assertions.assertEquals(sessionController.isUserLoggedIn(token), true);
    }

    @Test
    public void getUserRoleTest() throws InvalidTokenException, NotLoggedINException, InvalidFormatException, PasswordIsWrongException, InvalidAuthenticationException {
        /** Exception Tests **/
        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> sessionController.getUserRole(token));
        Assertions.assertEquals(ex.getMessage(),"you are not login to have a role.");
        /** Exception Tests **/

        authenticationController.login("aria","aria",token);
        Assertions.assertEquals(sessionController.getUserRole(token),Role.ADMIN);
    }
}
