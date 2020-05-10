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

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void registerTest() throws InvalidTokenException, InvalidFormatException, NoAccessException, InvalidAuthenticationException {
        /**Exception Tests**/
        Exception ex = Assert.assertThrows(NoAccessException.class, () -> authenticationController.register(
                new Account("AryaRezaei","1234",Role.ADMIN),token));
        Assert.assertEquals(ex.getMessage(),"You are not allowed to do that.");
        ex = Assert.assertThrows(InvalidFormatException.class, () -> authenticationController.register(
                new Account("Nigga"," ",Role.CUSTOMER),token));
        Assert.assertEquals(ex.getMessage(),"Password format is incorrect.");
        ex = Assert.assertThrows(InvalidFormatException.class, () -> authenticationController.register(
                new Account("","1234",Role.CUSTOMER),token));
        Assert.assertEquals(ex.getMessage(),"Username format is incorrect.");
        ex = Assert.assertThrows(InvalidAuthenticationException.class, () -> authenticationController.register(
                new Account("test1","1234",Role.CUSTOMER),token));
        Assert.assertEquals(ex.getMessage(),"Username is already taken.");
        /**End of Exception Tests**/
        authenticationController.register(new Account("Arya","1234",Role.SELLER),token);
        Assert.assertEquals("Arya",userRepository.getUserByName("Arya").getUsername());
        authenticationController.register(new Account("Tataloo","124",Role.CUSTOMER),token);
        Assert.assertEquals("Tataloo",userRepository.getUserByName("Tataloo").getUsername());
    }

    @Test
    public void loginTest() {

    }


}
