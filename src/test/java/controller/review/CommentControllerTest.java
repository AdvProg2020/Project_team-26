package controller.review;

import controller.account.AuthenticationController;
import controller.account.ShowUserController;
import exception.*;
import model.Comment;
import model.Session;
import model.repository.RepositoryContainer;
import model.repository.UserRepository;

import org.junit.jupiter.api.*;

import javax.naming.AuthenticationException;
import java.util.concurrent.CompletionException;
import org.junit.jupiter.api.BeforeEach;

public class CommentControllerTest {

    private RepositoryContainer repositoryContainer;
    private UserRepository userRepository;
    private String token;
    private AuthenticationController authenticationController;
    private CommentController commentController;

    @BeforeEach
    public void setup() {
        repositoryContainer = new RepositoryContainer();
        token = Session.addSession();
        authenticationController = new AuthenticationController(repositoryContainer);
        commentController = new CommentController(repositoryContainer);
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
    }


    @Test
    public void addACommentTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, AuthenticationException, PasswordIsWrongException, NoAccessException, AlreadyLoggedInException {

        /** Exception Tests **/

        Exception ex = Assertions.assertThrows(NoAccessException.class, () ->
                commentController.addAComment("BAD",0,token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");

        ex = Assertions.assertThrows(NoAccessException.class, () ->
                commentController.addAComment("Good",0,token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");

        authenticationController.login("test1","password1",token);
        ex = Assertions.assertThrows(NoAccessException.class, () ->
                commentController.addAComment("Good",0,token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");

        /** Exception ends **/
        
        authenticationController.login("test8","password8",token);
        commentController.addAComment("Good",0,token);

    }


}
