package controller.review;

import controller.account.AuthenticationController;
import exception.*;
import model.Session;
import repository.CommentRepository;
import repository.RepositoryContainer;
import repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.AuthenticationException;

public class CommentControllerTest {

    private RepositoryContainer repositoryContainer;
    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private String token;
    private AuthenticationController authenticationController;
    private CommentController commentController;

    @BeforeEach
    public void setup() {
        repositoryContainer = new RepositoryContainer("sql");
        token = Session.addSession();
        authenticationController = new AuthenticationController(repositoryContainer);
        commentController = new CommentController(repositoryContainer);
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
        commentRepository = (CommentRepository) repositoryContainer.getRepository("CommentRepository");
    }


    @Test
    public void addACommentTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, AuthenticationException, PasswordIsWrongException, NoAccessException, AlreadyLoggedInException, NotLoggedINException {

        /** Exception Tests **/

        Exception ex = Assertions.assertThrows(NoAccessException.class, () ->
                commentController.addComment("BAD", "New Comment", 0, token));
        Assertions.assertEquals(ex.getMessage(), "You are not allowed to do that.");

        ex = Assertions.assertThrows(NoAccessException.class, () ->
                commentController.addComment("Good", "New Comment", 0, token));
        Assertions.assertEquals(ex.getMessage(), "You are not allowed to do that.");

        authenticationController.login("arya", "arya", token);
        ex = Assertions.assertThrows(NoAccessException.class, () ->
                commentController.addComment("Good", "New Comment", 0, token));
        Assertions.assertEquals(ex.getMessage(), "You are not allowed to do that.");

        /** Exception ends **/

        authenticationController.login("customer", "1234", token);
        commentController.addComment("Good", "New Comment", 1, token);
    }

    @Test
    public void removeACommentTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NoAccessException, NotLoggedINException, NoObjectIdException {

        /** Exceptions **/

        Exception ex = Assertions.assertThrows(NoAccessException.class, () -> commentController.removeComment(1, token));
        Assertions.assertEquals(ex.getMessage(), "You are not allowed to do that.");

        authenticationController.login("test1","1234",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> commentController.removeComment(1,token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");
        Assertions.assertEquals(commentRepository.getById(1).getText(),"Good");

        /** End of Exceptions**/
        authenticationController.logout(token);

        authenticationController.login("arya", "arya", token);
        commentController.removeComment(1, token);
    }



}
