package controller.account;

import exception.InvalidAuthenticationException;
import exception.InvalidFormatException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import model.Role;
import model.Session;
import model.repository.RepositoryContainer;
import model.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        ArrayList<Account> testAccounts = new ArrayList<>();
        setAccount(testAccounts);
        for (Account account : testAccounts) {
            try {
                authenticationController.register(account, token);
                Assert.assertEquals(account.getUsername(), userRepository.getUserByName(account.getUsername()).getUsername());
            } catch (InvalidAuthenticationException e) {
                Assert.assertEquals(e.getMessage(), "Username is already taken.");
            } catch (InvalidFormatException e) {
                Assert.assertEquals(e.getMessage(), e.getFieldName() + " format is wrong");
            } catch (NoAccessException e) {
                Assert.assertEquals(e.getMessage(), "You are not allowed to do that.");
            } catch (InvalidTokenException e) {
                e.printStackTrace();
            }
        }
    }


    private void setAccount(ArrayList<Account> testAccounts) {
        testAccounts.add(new Account("test1","password", Role.CUSTOMER));
        testAccounts.add(new Account("Master","password",Role.ADMIN));
        testAccounts.add(new Account("Master2","Password",Role.ADMIN));
    }

}
