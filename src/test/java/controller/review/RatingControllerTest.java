package controller.review;

import controller.account.AuthenticationController;
import controller.interfaces.review.IRatingController;
import exception.*;
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
    private RatingRepository ratingRepository;
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
        ratingRepository = (RatingRepository) repositoryContainer.getRepository("RatingRepository");
        productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
    }

    @Test
    public void addARatingTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NoAccessException, NotBoughtTheProductException, NotLoggedINException {
        /** Exception Tests **/

        Exception ex = Assertions.assertThrows(NoAccessException.class, () -> ratingController.addARating(5.0,
                2,token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");

        ex = Assertions.assertThrows(NotLoggedINException.class, () -> ratingController.removeRating(6,token));
        Assertions.assertEquals(ex.getMessage(),"You are not Logged in.");

        authenticationController.login("test8","password8",token);
        ex = Assertions.assertThrows(NotBoughtTheProductException.class, () -> ratingController.addARating(5.0,
                2,token));
        Assertions.assertEquals(ex.getMessage(),"You have not bought this product");
        authenticationController.logout(token);

        authenticationController.login("test6","password6",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> ratingController.addARating(5.0,
                2,token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");
        authenticationController.logout(token);

        authenticationController.login("test8","password8",token);
        ratingController.addARating(5.0,1,token);
        authenticationController.logout(token);
        authenticationController.login("test9","password9",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> ratingController.removeRating(6,token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");
        authenticationController.logout(token);

        /** Exception Tests **/

        authenticationController.login("test8","password8",token);
        ratingController.addARating(5.0,1,token);
        Assertions.assertEquals(ratingRepository.getById(6).getScore(),5.0);
        ratingController.removeRating(6,token);
        Assertions.assertEquals(null,ratingRepository.getById(6));

    }

}
