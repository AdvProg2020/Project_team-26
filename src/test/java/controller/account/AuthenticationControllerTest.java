package controller.account;

import controller.interfaces.account.IAuthenticationController;
import exception.InvalidAuthenticationException;
import exception.InvalidFormatException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import model.Session;
import model.User;
import model.repository.RepositoryContainer;
import model.repository.fake.FakeUserRepository;
import org.junit.Assert;
import org.junit.Test;

public class AuthenticationControllerTest {
    RepositoryContainer repositoryContainer;
    FakeUserRepository fakeUserRepository;
    String token;
    private AuthenticationController authenticationController;

    @Test
    public void registerTest() {
        repositoryContainer = new RepositoryContainer();
        token = Session.addSession();
        authenticationController = new AuthenticationController(repositoryContainer);
        Account account = new Account();
        setAccount(account);
        try {
            authenticationController.register(account, token);
            Assert.assertNotEquals(fakeUserRepository.getUserByName("Arya"),null);
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
        account.setRole("Admin");
        account.setPassword("Password");
        account.setUsername("Arya");
    }

}
