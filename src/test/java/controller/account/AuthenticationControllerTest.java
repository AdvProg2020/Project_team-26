package controller.account;

import exception.InvalidAuthenticationException;
import exception.InvalidFormatException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import model.Session;
import model.repository.RepositoryContainer;
import model.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationControllerTest {
    private RepositoryContainer repositoryContainer;
    private UserRepository userRepository;
    private String token;
    private AuthenticationController authenticationController;

    @Before
    public void setup() {
        repositoryContainer = new RepositoryContainer();
        token = Session.addSession();
        authenticationController = new AuthenticationController(repositoryContainer);
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
    }

    @Test
    public void registerTest() {
        Account account = new Account();
        setAccount(account);
        try {
            authenticationController.register(account, token);
            Assert.assertEquals(account.makeUser(), userRepository.getUserByName("Arya"));
        } catch (InvalidAuthenticationException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }
    }


    private void setAccount(Account account) {
        account.setRole("manager");
        account.setPassword("Password");
        account.setUsername("Arya");
        account.setFirstName("ARYA");
        account.setLastName("Jalali");
        account.setEmail("ASDASDKASDA");
    }

}
