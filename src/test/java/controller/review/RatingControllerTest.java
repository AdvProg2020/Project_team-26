package controller.review;

import controller.account.AuthenticationController;
import model.Session;
import model.repository.CommentRepository;
import model.repository.RatingRepository;
import model.repository.RepositoryContainer;
import model.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RatingControllerTest {

    private RepositoryContainer repositoryContainer;
    private UserRepository userRepository;
    private RatingRepository commentRepository;
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
        commentRepository = (RatingRepository) repositoryContainer.getRepository("RatingRepository");
    }

    @Test
    public void addARatingTest() {

    }

}
