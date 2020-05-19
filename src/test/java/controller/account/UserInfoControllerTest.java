package controller.account;

import antlr.ASdebug.ASDebugStream;
import exception.*;
import model.Role;
import model.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.RepositoryContainer;
import repository.UserRepository;

import java.beans.Transient;
import java.net.UnknownServiceException;
import java.rmi.NoSuchObjectException;

public class UserInfoControllerTest {

    private RepositoryContainer repositoryContainer;
    private UserRepository userRepository;
    private String token;
    private AuthenticationController authenticationController;
    private ShowUserController showUserController;
    private UserInfoController userInfoController;

    @BeforeEach
    public void setup() {
        repositoryContainer = new RepositoryContainer("sql");
        token = Session.addSession();
        authenticationController = new AuthenticationController(repositoryContainer);
        showUserController = new ShowUserController(repositoryContainer);
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
        userInfoController = new UserInfoController(repositoryContainer);
    }


    @Test
    public void changePassword() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException {

        /** Exception Tests **/

        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> userInfoController.changePassword("a", "b", token));
        Assertions.assertEquals(ex.getMessage(),"You are not logged in.");

        authenticationController.login("aria","aria",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> userInfoController.changePassword("pp","ss",token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");

        /** Exception Tests **/
    }

    @Test
    public void changeInfoTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException, NoSuchField {

        /** Exception Tests **/

        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> userInfoController.changeInfo("Username","sosk",token));
        Assertions.assertEquals(ex.getMessage(),"You are not logged in.");

        authenticationController.login("aria","aria",token);

        ex = Assertions.assertThrows(NoSuchField.class, () -> userInfoController.changeInfo("Company Name","test",token));
        Assertions.assertEquals(ex.getMessage(),"No Such Field exists.");

        ex = Assertions.assertThrows(InvalidFormatException.class, () -> userInfoController.changeInfo("Email","soso",token));
        Assertions.assertEquals(ex.getMessage(),"Email format is incorrect.");

        ex = Assertions.assertThrows(NoSuchField.class, () -> userInfoController.changeInfo("dodol","o",token));
        Assertions.assertEquals(ex.getMessage(), "No Such Field exists");

        /** Exception Tests **/

        userInfoController.changeInfo("Email","test@gmail.com",token);
        Assertions.assertEquals(userRepository.getUserByUsername("aria").getEmail(),"test@gmail.com");

        userInfoController.changeInfo("LastName","mamad",token);
        Assertions.assertEquals(userRepository.getUserByUsername("aria").getFullName(),"null mamad");

        userInfoController.changeInfo("FirstName","arya",token);
        Assertions.assertEquals(userRepository.getUserByUsername("aria").getFullName(),"arya mamad");

    }

    @Test
    public void getRoleTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException {

        /** Exception Tests **/
        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> userInfoController.getRole(token));
        Assertions.assertEquals(ex.getMessage(),"You are not logged in");
        /** Exception Tests **/

        authenticationController.login("aria","aria",token);
        Assertions.assertEquals(userInfoController.getRole(token), "ADMIN");
    }

    @Test
    public void getCompanyName() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException, NoSuchField {

        /** Exception Tests **/
        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> userInfoController.getCompanyName(token));
        Assertions.assertEquals(ex.getMessage(),("You are not Logged in."));

        authenticationController.login("aria","aria",token);
        ex = Assertions.assertThrows(NoSuchField.class, () -> userInfoController.getCompanyName(token));
        Assertions.assertEquals(ex.getMessage(),"This field does not exist.");
        authenticationController.logout(token);
        /** Exception Tests **/

        authenticationController.login("test1","test1",token);
        Assertions.assertEquals(userInfoController.getCompanyName(token),null);
    }

    @Test
    public void getBalanceTest() throws InvalidTokenException, NotLoggedINException, InvalidFormatException, PasswordIsWrongException, InvalidAuthenticationException {

        /** Exception Tests **/
        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> userInfoController.getBalance(token));
        Assertions.assertEquals(ex.getMessage(),"You are not logged in");
        /** Exception Tests **/

        authenticationController.login("aria","aria",token);
        Assertions.assertEquals("7654",userInfoController.getBalance(token));
    }

    @Test
    public void changePasswordTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException {
        /** Exception Tests **/
        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> userInfoController.changePassword("tst1","tst2",token));
        Assertions.assertEquals(ex.getMessage(),"You are not logged in.");

        authenticationController.login("aria","aria",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> userInfoController.changePassword("test2","tt",token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");

        /** Exception Tests **/
    }

}
