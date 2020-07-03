package controller.account;

import exception.*;
import model.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.RepositoryContainer;
import repository.UserRepository;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

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

        authenticationController.login("arya","arya",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> userInfoController.changePassword("pp","ss",token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");

        /** Exception Tests **/
    }

    @Test
    public void changeInfoTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException, NoSuchField {

        /** Exception Tests **/

        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> userInfoController.changeInfo("Username","sosk",token));
        Assertions.assertEquals(ex.getMessage(),"You are not logged in.");

        authenticationController.login("arya","arya",token);

        ex = Assertions.assertThrows(NoSuchField.class, () -> userInfoController.changeInfo("Company Name","test",token));
        Assertions.assertEquals(ex.getMessage(),"No Such Field exists.");

        ex = Assertions.assertThrows(InvalidFormatException.class, () -> userInfoController.changeInfo("Email","soso",token));
        Assertions.assertEquals(ex.getMessage(),"Email format is incorrect.");

        ex = Assertions.assertThrows(NoSuchField.class, () -> userInfoController.changeInfo("dodol","o",token));
        Assertions.assertEquals(ex.getMessage(), "No Such Field exists");

        /** Exception Tests **/

        String randomEmail = getRandomEmail();

        userInfoController.changeInfo("Email",randomEmail,token);
        Assertions.assertEquals(userRepository.getUserByUsername("arya").getEmail(),randomEmail);

        userInfoController.changeInfo("LastName","mamad",token);
        Assertions.assertEquals(userRepository.getUserByUsername("arya").getFullName(),"arya mamad");

        userInfoController.changeInfo("FirstName","arya",token);
        Assertions.assertEquals(userRepository.getUserByUsername("arya").getFullName(),"arya mamad");

        authenticationController.login("arya","arya",token);
        Map<String,String> newInfo =  new HashMap<>();
        newInfo.put("Email","jafar@yahoo.com");
        newInfo.put("LastName","bagherqomi");
        userInfoController.changeInfo(newInfo,token);
        Assertions.assertEquals("bagherqomi",userRepository.getUserByUsername("arya").getLastName());

    }

    @Test
    public void getRoleTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException {

        /** Exception Tests **/
        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> userInfoController.getRole(token));
        Assertions.assertEquals(ex.getMessage(),"You are not logged in");
        /** Exception Tests **/

        authenticationController.login("arya","arya",token);
        Assertions.assertEquals(userInfoController.getRole(token), "ADMIN");
    }

    @Test
    public void getCompanyName() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException, NoSuchField {

        /** Exception Tests **/
        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> userInfoController.getCompanyName(token));
        Assertions.assertEquals(ex.getMessage(),("You are not Logged in."));

        authenticationController.login("arya","arya",token);
        ex = Assertions.assertThrows(NoSuchField.class, () -> userInfoController.getCompanyName(token));
        Assertions.assertEquals(ex.getMessage(),"This field does not exist.");
        authenticationController.logout(token);
        /** Exception Tests **/

        authenticationController.login("seller","1234",token);
        Assertions.assertEquals(userInfoController.getCompanyName(token),null);
    }

    @Test
    public void getBalanceTest() throws InvalidTokenException, NotLoggedINException, InvalidFormatException, PasswordIsWrongException, InvalidAuthenticationException {

        /** Exception Tests **/
        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> userInfoController.getBalance(token));
        Assertions.assertEquals(ex.getMessage(),"You are not logged in");
        /** Exception Tests **/

        authenticationController.login("customer","1234",token);
        Assertions.assertEquals(userInfoController.getBalance(token),userInfoController.getBalance(token));
    }

    @Test
    public void changePasswordTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException {
        /** Exception Tests **/
        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> userInfoController.changePassword("tst1","tst2",token));
        Assertions.assertEquals(ex.getMessage(),"You are not logged in.");

        authenticationController.login("arya","arya",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> userInfoController.changePassword("test2","tt",token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");

        /** Exception Tests **/
    }

    private String getRandomEmail() {
        String email = "";
        email += LocalTime.now();
        email += "@gmail.com";
        return email;
    }

}
