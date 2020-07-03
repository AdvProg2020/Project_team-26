package controller.account;

import exception.*;
import model.enums.Role;
import model.Session;
import repository.RepositoryContainer;
import repository.UserRepository;
import org.junit.jupiter.api.*;

import javax.naming.AuthenticationException;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthenticationControllerTest {
    private RepositoryContainer repositoryContainer;
    private UserRepository userRepository;
    private String token;
    private AuthenticationController authenticationController;

    @BeforeEach
    public void setup() {
        repositoryContainer = new RepositoryContainer("sql");
        token = Session.addSession();
        authenticationController = new AuthenticationController(repositoryContainer);
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
    }

    @Test
    public void registerTest() throws InvalidTokenException, InvalidFormatException, NoAccessException, InvalidAuthenticationException, PasswordIsWrongException, AuthenticationException, AlreadyLoggedInException {
        /**Exception Tests**/
        Exception ex = assertThrows(NoAccessException.class, () -> authenticationController.register(
                new Account("AryaRezaei","1234",Role.ADMIN,getRandomEmail()),token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");

        ex = assertThrows(InvalidFormatException.class, () -> authenticationController.register(
                new Account("Nigga"," ",Role.CUSTOMER),token));
        Assertions.assertEquals(ex.getMessage(),"Password format is incorrect.");

        ex = assertThrows(InvalidFormatException.class, () -> authenticationController.register(
                new Account("","1234",Role.CUSTOMER),token));
        Assertions.assertEquals(ex.getMessage(),"Username format is incorrect.");

        ex = assertThrows(InvalidAuthenticationException.class, () -> authenticationController.register(
                new Account("test1","1234",Role.CUSTOMER,"niga@yahoo.com"),token));
        Assertions.assertEquals(ex.getMessage(),"Email is taken.");

        ex = assertThrows(InvalidAuthenticationException.class, () -> authenticationController.register(
                new Account("test1","1234",Role.CUSTOMER,"niga5@yahoo.com"),token));
        Assertions.assertEquals(ex.getMessage(),"Username is already taken.");

        ex = assertThrows(NotLoggedINException.class, () -> authenticationController.logout(token));
        Assertions.assertEquals(ex.getMessage(),"You are not logged in.");
        /**End of Exception Tests**/

        String randomName = getRandomName();

        authenticationController.register(new Account(randomName,"1234",Role.SELLER,getRandomEmail()),token);
        Assertions.assertEquals(randomName,userRepository.getUserByUsername(randomName).getUsername());
        randomName = getRandomName();
        authenticationController.register(new Account(randomName,"124",Role.CUSTOMER,getRandomEmail()),token);
        Assertions.assertEquals(randomName,userRepository.getUserByUsername(randomName).getUsername());

        /** Logging in and then doing stuff**/

        randomName = getRandomName();

        authenticationController.login("arya","arya",token);
        authenticationController.register(new Account(randomName,"admin",Role.ADMIN,getRandomEmail()),token);
        Assertions.assertEquals(randomName,userRepository.getUserByUsername(randomName).getUsername());

    }

    @Test
    public void loginTest() {
        /** Exception Tests**/
        Exception ex = assertThrows(InvalidAuthenticationException.class, () -> authenticationController.login(
                "arya","password2",token));
        Assertions.assertEquals(ex.getMessage(),"Password is wrong");
        ex = assertThrows(InvalidAuthenticationException.class, () -> authenticationController.login(
                "mamad","password",token));
        Assertions.assertEquals(ex.getMessage(),"Username is invalid.");
        /** End of Exception Tests**/
    }

    private String getRandomName() {
        String randomName = "randomName";
        randomName += LocalTime.now();
        return randomName;
    }


    private String getRandomEmail() {
        String email = "";
        email += LocalTime.now();
        email += "@gmail.com";
        return email;
    }


}
