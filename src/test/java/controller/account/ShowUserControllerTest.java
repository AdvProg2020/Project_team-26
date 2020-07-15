package controller.account;

import exception.*;
import model.Session;
import repository.RepositoryContainer;
import repository.UserRepository;
import org.junit.jupiter.api.*;

import javax.naming.AuthenticationException;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShowUserControllerTest {

    private RepositoryContainer repositoryContainer;
    private UserRepository userRepository;
    private String token;
    private AuthenticationController authenticationController;
    private ShowUserController showUserController;

    @BeforeEach
    public void setup() {
        repositoryContainer = new RepositoryContainer("sql");
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

        /** Normal Tests**/

        String randomName = getRandomName();
        authenticationController.login("arya","arya",token);
        Assertions.assertEquals(showUserController.getUsers(token),userRepository.getAll());
    }

    @Test
    public void getUserByTokenTest() throws InvalidTokenException, InvalidFormatException, PasswordIsWrongException, InvalidAuthenticationException {
        authenticationController.login("arya","arya",token);
        Assertions.assertNotEquals(null,showUserController.getUserByToken(token));
    }

    @Test
    public void getManagersTest() {
        Assertions.assertNotEquals(showUserController.getManagers(28),null);
    }

    private String getRandomName() {
        String randomName = "randomName";
        randomName += LocalTime.now();
        return randomName;
    }


}
