package controller.account;

import exception.*;
import model.Role;
import model.Session;
import model.repository.RepositoryContainer;
import model.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeAll;

import javax.naming.AuthenticationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthenticationControllerTest {
    private RepositoryContainer repositoryContainer;
    private UserRepository userRepository;
    private String token;
    private AuthenticationController authenticationController;

    @BeforeEach
    public void setup() {
        repositoryContainer = new RepositoryContainer();
        token = Session.addSession();
        authenticationController = new AuthenticationController(repositoryContainer);
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
    }

    @Test
    public void registerTest() throws InvalidTokenException, InvalidFormatException, NoAccessException, InvalidAuthenticationException, PasswordIsWrongException, AuthenticationException, AlreadyLoggedInException {
        /**Exception Tests**/
        Exception ex = assertThrows(NoAccessException.class, () -> authenticationController.register(
                new Account("AryaRezaei","1234",Role.ADMIN),token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");

        ex = assertThrows(InvalidFormatException.class, () -> authenticationController.register(
                new Account("Nigga"," ",Role.CUSTOMER),token));
        Assertions.assertEquals(ex.getMessage(),"Password format is incorrect.");

        ex = assertThrows(InvalidFormatException.class, () -> authenticationController.register(
                new Account("","1234",Role.CUSTOMER),token));
        Assertions.assertEquals(ex.getMessage(),"Username format is incorrect.");

        ex = assertThrows(InvalidAuthenticationException.class, () -> authenticationController.register(
                new Account("test1","1234",Role.CUSTOMER),token));
        Assertions.assertEquals(ex.getMessage(),"Username is already taken.");

        ex = assertThrows(NotLoggedINException.class, () -> authenticationController.logout(token));
        Assertions.assertEquals(ex.getMessage(),"You are not logged in.");
        /**End of Exception Tests**/

        authenticationController.register(new Account("Arya","1234",Role.SELLER),token);
        Assertions.assertEquals("Arya",userRepository.getUserByName("Arya").getUsername());
        authenticationController.register(new Account("Tataloo","124",Role.CUSTOMER),token);
        Assertions.assertEquals("Tataloo",userRepository.getUserByName("Tataloo").getUsername());

        /** Logging in and then doing stuff**/

        authenticationController.login("test1","password1",token);
        authenticationController.register(new Account("Admin","admin",Role.ADMIN),token);
        Assertions.assertEquals("Admin",userRepository.getUserByName("Admin").getUsername());

    }

    @Test
    public void loginTest() {
        /** Exception Tests**/
        Exception ex = assertThrows(PasswordIsWrongException.class, () -> authenticationController.login(
                "test1","password2",token));
        Assertions.assertEquals(ex.getMessage(),"Password is wrong");
        ex = assertThrows(InvalidAuthenticationException.class, () -> authenticationController.login(
                "mamad","password",token));
        Assertions.assertEquals(ex.getMessage(),"Username is invalid.");
        /** End of Exception Tests**/

    }


}
