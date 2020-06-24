package controller;

import controller.RequestController;
import controller.account.AuthenticationController;
import controller.account.ShowUserController;
import controller.account.UserInfoController;
import exception.*;
import model.OffRequest;
import model.ProductRequest;
import model.ProductSellerRequest;
import model.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.RepositoryContainer;
import repository.UserRepository;

import java.util.List;

public class RequestControllerTest {

    private RepositoryContainer repositoryContainer;
    private UserRepository userRepository;
    private String token;
    private AuthenticationController authenticationController;
    private ShowUserController showUserController;
    private UserInfoController userInfoController;
    private RequestController requestController;

    @BeforeEach
    public void setup() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException {
        repositoryContainer = new RepositoryContainer("sql");
        token = Session.addSession();
        authenticationController = new AuthenticationController(repositoryContainer);
        showUserController = new ShowUserController(repositoryContainer);
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
        userInfoController = new UserInfoController(repositoryContainer);
        requestController = new RequestController(repositoryContainer);
        authenticationController.login("aria","aria",token);
    }


    @Test
    public void acceptOffRequestTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NoAccessException, NotLoggedINException {

        /** Exception Tests **/

        authenticationController.logout(token);
        Exception ex = Assertions.assertThrows(NotLoggedINException.class,() -> requestController.acceptOffRequest(2,token));
        Assertions.assertEquals(ex.getMessage(),"You are not logged in");

        authenticationController.login("test4","test4",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> requestController.acceptOffRequest(2,token));
        Assertions.assertEquals(ex.getMessage(),"You must be an Admin to do this.");
        authenticationController.logout(token);
        /** Exception Tests **/


        authenticationController.login("aria","aria",token);
        requestController.acceptOffRequest(1,token);

    }

    @Test
    public void rejectOffRequest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NoAccessException, NotLoggedINException {

        /** Exception Tests **/

        authenticationController.logout(token);
        Exception ex = Assertions.assertThrows(NotLoggedINException.class,() -> requestController.rejectOffRequest(1,token));
        Assertions.assertEquals(ex.getMessage(),"You are not logged in");

        authenticationController.login("test4","test4",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> requestController.rejectOffRequest(1,token));
        Assertions.assertEquals(ex.getMessage(),"You must be an Admin to do this.");
        authenticationController.logout(token);
        /** Exception Tests **/

        authenticationController.login("aria","aria",token);
        requestController.rejectOffRequest(2,token);
    }

    @Test
    public void acceptProductRequestTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NoAccessException, NotLoggedINException {
        /** Exception Tests **/

        authenticationController.logout(token);
        Exception ex = Assertions.assertThrows(NotLoggedINException.class,() -> requestController.acceptProductRequest(1,token));
        Assertions.assertEquals(ex.getMessage(),"You are not logged in");

        authenticationController.login("test4","test4",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> requestController.acceptProductRequest(1,token));
        Assertions.assertEquals(ex.getMessage(),"You must be an Admin to do this.");
        authenticationController.logout(token);
        /** Exception Tests **/


        authenticationController.login("aria","aria",token);

        requestController.acceptProductRequest(2,token);
    }


    @Test
    public void rejectProductRequestTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NoAccessException, NotLoggedINException {

        /** Exception Tests **/

        authenticationController.logout(token);
        Exception ex = Assertions.assertThrows(NotLoggedINException.class,() -> requestController.rejectProductRequest(1,token));
        Assertions.assertEquals(ex.getMessage(),"You are not logged in");

        authenticationController.login("test4","test4",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> requestController.rejectProductRequest(1,token));
        Assertions.assertEquals(ex.getMessage(),"You must be an Admin to do this.");
        authenticationController.logout(token);
        /** Exception Tests **/


        authenticationController.login("aria","aria",token);

        requestController.rejectProductRequest(2,token);
    }

    @Test
    public void acceptProductSellerRequestTest() throws InvalidTokenException, NoAccessException, NotLoggedINException, InvalidFormatException, PasswordIsWrongException, InvalidAuthenticationException {

        /** Exception Tests **/

        authenticationController.logout(token);
        Exception ex = Assertions.assertThrows(NotLoggedINException.class,() -> requestController.acceptProductSellerRequest(1,token));
        Assertions.assertEquals(ex.getMessage(),"You are not logged in");

        authenticationController.login("test4","test4",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> requestController.acceptProductSellerRequest(1,token));
        Assertions.assertEquals(ex.getMessage(),"You must be an Admin to do this.");
        authenticationController.logout(token);
        /** Exception Tests **/


        authenticationController.login("aria","aria",token);

        requestController.acceptProductSellerRequest(2,token);
    }

    @Test
    public void rejectProductSellerRequestTest() throws InvalidTokenException, NoAccessException, NotLoggedINException, InvalidFormatException, PasswordIsWrongException, InvalidAuthenticationException {

        /** Exception Tests **/

        authenticationController.logout(token);
        Exception ex = Assertions.assertThrows(NotLoggedINException.class,() -> requestController.rejectProductSellerRequest(1,token));
        Assertions.assertEquals(ex.getMessage(),"You are not logged in");

        authenticationController.login("test4","test4",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> requestController.rejectProductSellerRequest(1,token));
        Assertions.assertEquals(ex.getMessage(),"You must be an Admin to do this.");
        authenticationController.logout(token);
        /** Exception Tests **/


        authenticationController.login("aria","aria",token);

        requestController.rejectProductSellerRequest(2,token);
    }







}
