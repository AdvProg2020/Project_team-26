package controller.review;

import controller.account.AuthenticationController;
import model.Session;
import model.repository.CommentRepository;
import model.repository.RepositoryContainer;
import model.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;

public class RatingControllerTest {

    private RepositoryContainer repositoryContainer;
    private UserRepository userRepository;
    private CommentRepository commentRepository;
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
        commentRepository = (CommentRepository) repositoryContainer.getRepository("CommentRepository");
    }
}
