package controller.review;

import repository.ProductRepository;
import repository.RateRepository;
import repository.RepositoryContainer;
import controller.account.AuthenticationController;
import exception.*;
import model.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.UserRepository;

public class RatingControllerTest {

    private RepositoryContainer repositoryContainer;
    private UserRepository userRepository;
    private RateRepository rateRepository;
    private ProductRepository productRepository;
    private String token;
    private AuthenticationController authenticationController;
    private CommentController commentController;
    private RatingController ratingController;


    @BeforeEach
    public void setup() {
        repositoryContainer = new RepositoryContainer("sql");
        token = Session.addSession();
        authenticationController = new AuthenticationController(repositoryContainer);
        ratingController = new RatingController(repositoryContainer);
        commentController = new CommentController(repositoryContainer);
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
        rateRepository = (RateRepository) repositoryContainer.getRepository("RatingRepository");
        productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
    }

    @Test
    public void addARatingTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NoAccessException, NotBoughtTheProductException, NotLoggedINException, NoObjectIdException {
        /** Exception Tests **/

        Exception ex = Assertions.assertThrows(NoAccessException.class, () -> ratingController.addRating(5.0,
                2,token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");

        authenticationController.login("test5","test5",token);
        ex = Assertions.assertThrows(NotBoughtTheProductException.class, () -> ratingController.addRating(5.0,
                2,token));
        Assertions.assertEquals(ex.getMessage(),"You have not bought this product");
        authenticationController.logout(token);

        authenticationController.login("aria","aria",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> ratingController.addRating(5.0,
                2,token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");
        authenticationController.logout(token);

        authenticationController.login("test8","test8",token);
        ratingController.addRating(5.0,1,token);
        authenticationController.logout(token);
        authenticationController.login("aria","aria",token);
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");
        authenticationController.logout(token);

        /** Exception Tests **/

        authenticationController.login("test8","test8",token);
        ratingController.addRating(5.0,1,token);
        Assertions.assertEquals(rateRepository.getById(6).getScore(),5.0);

    }

}
