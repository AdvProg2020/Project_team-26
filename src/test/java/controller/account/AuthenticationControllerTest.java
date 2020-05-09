package controller.account;

import controller.interfaces.account.IAuthenticationController;
import exception.InvalidAuthenticationException;
import exception.InvalidFormatException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import model.Role;
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
            Assert.assertEquals(account.makeUser(),fakeUserRepository.getUserByName("Arya"));
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
