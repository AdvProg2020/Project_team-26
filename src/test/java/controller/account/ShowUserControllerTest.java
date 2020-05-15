package controller.account;

import exception.*;
import model.Session;
import repository.RepositoryContainer;
import repository.UserRepository;
import org.junit.jupiter.api.*;

import javax.naming.AuthenticationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShowUserControllerTest {

    private RepositoryContainer repositoryContainer;
    private UserRepository userRepository;
    private String token;
    private AuthenticationController authenticationController;
    private ShowUserController showUserController;

    @BeforeEach
    public void setup() {
        repositoryContainer = new RepositoryContainer();
        token = Session.addSession();
        authenticationController = new AuthenticationController(repositoryContainer);
        showUserController = new ShowUserController(repositoryContainer);
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
    }

    @Test
    public void deleteTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, AuthenticationException, PasswordIsWrongException, NotLoggedINException, NoAccessException, AlreadyLoggedInException, NoObjectIdException {

        /** Exception Tests Without Login**/
        Exception ex = assertThrows(NoAccessException.class, () -> showUserController.getUserInfo(token));
        Assertions.assertEquals(ex.getMessage(), "You are not allowed to do that.");

        ex = assertThrows(NoAccessException.class, () -> showUserController.getUsers(token));
        Assertions.assertEquals(ex.getMessage(), "You are not allowed to do that.");

        ex = assertThrows(NoAccessException.class, () -> showUserController.getUserById(12,token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");

        ex = assertThrows(NoAccessException.class, () -> showUserController.getUserByName("Test",token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");

        /**Exception Tests After Login**/

        authenticationController.login("test7","password7",token);
        ex = assertThrows(NoAccessException.class, () -> showUserController.delete("test1",token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");

        ex = assertThrows(NoAccessException.class, () -> showUserController.getUsers(token));
        Assertions.assertEquals(ex.getMessage(), "You are not allowed to do that.");

        ex = assertThrows(NoAccessException.class, () -> showUserController.getUserByName("Test",token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");

        ex = assertThrows(NoAccessException.class, () -> showUserController.getUserById(12,token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");

        authenticationController.logout(token);
        /** Normal Tests**/

        authenticationController.login("test1","password1",token);
        showUserController.delete("test2",token);
        Assertions.assertEquals(userRepository.getUserByName("test2"),null);
        Assertions.assertEquals(userRepository.getUserByName("test2"),null);

        Assertions.assertEquals(showUserController.getUsers(token),userRepository.getAll());
    }


}
