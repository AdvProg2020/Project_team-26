package controller.review;

import controller.account.AuthenticationController;
import controller.interfaces.review.IRatingController;
import exception.NoAccessException;
import model.Customer;
import model.Product;
import model.Rate;
import model.Session;
import model.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RatingControllerTest {

    private RepositoryContainer repositoryContainer;
    private UserRepository userRepository;
    private RatingRepository commentRepository;
    private ProductRepository productRepository;
    private String token;
    private AuthenticationController authenticationController;
    private CommentController commentController;
    private RatingController ratingController;


    @BeforeEach
    public void setup() {
        repositoryContainer = new RepositoryContainer();
        token = Session.addSession();
        authenticationController = new AuthenticationController(repositoryContainer);
        ratingController = new RatingController(repositoryContainer);
        commentController = new CommentController(repositoryContainer);
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
        commentRepository = (RatingRepository) repositoryContainer.getRepository("RatingRepository");
        productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
    }

    @Test
    public void addARatingTest() {
        /** Exception Tests **/
        Exception ex = Assertions.assertThrows(NoAccessException.class, () -> ratingController.addARating(5.0,
                2,token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");
    }

}
