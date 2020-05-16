package controller;

import antlr.ASdebug.ASDebugStream;
import controller.account.AuthenticationController;
import exception.*;
import model.Role;
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
        repositoryContainer = new RepositoryContainer();
        sessionController = new SessionController(repositoryContainer);
        authenticationController = new AuthenticationController(repositoryContainer);
        token = Session.addSession();
    }


    @Test
    public void createTokenTest() {
        Assertions.assertEquals("a1", sessionController.createToken());
    }

    @Test
    public void getUser() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException {
        authenticationController.login("test1", "password1", token);
        Assertions.assertEquals(sessionController.getUser(token).getRole(), Role.ADMIN);
    }

    @Test
    public void isUserLoggedIn() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException {
        Assertions.assertEquals(sessionController.isUserLoggedIn(token), "false");
        authenticationController.login("test1", "password1", token);
        Assertions.assertEquals(sessionController.isUserLoggedIn(token), "true");
    }

    @Test
    public void getUserRoleTest() throws InvalidTokenException, NotLoggedINException, InvalidFormatException, PasswordIsWrongException, InvalidAuthenticationException {
        /** Exception Tests **/
        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> sessionController.getUserRole(token));
        Assertions.assertEquals(ex.getMessage(),"you are not login to have a role.");
        /** Exception Tests **/

        authenticationController.login("test1","password1",token);
        Assertions.assertEquals(sessionController.getUserRole(token),Role.ADMIN);
    }
}
