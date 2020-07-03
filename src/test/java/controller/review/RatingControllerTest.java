package controller.review;

import controller.cart.CartController;
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
    private CartController cartController;


    @BeforeEach
    public void setup() {
        repositoryContainer = new RepositoryContainer("sql");
        token = Session.addSession();
        authenticationController = new AuthenticationController(repositoryContainer);
        ratingController = new RatingController(repositoryContainer);
        commentController = new CommentController(repositoryContainer);
        cartController = new CartController(repositoryContainer);
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
        rateRepository = (RateRepository) repositoryContainer.getRepository("RatingRepository");
        productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
    }

    @Test
    public void addARatingTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NoAccessException, NotBoughtTheProductException, NotLoggedINException, NoObjectIdException, NotEnoughCreditException, NotEnoughProductsException, InvalidIdException {
        /** Exception Tests **/

        Exception ex = Assertions.assertThrows(NoAccessException.class, () -> ratingController.rate(5.0,
                2,token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");

        authenticationController.login("test1","1234",token);
        ex = Assertions.assertThrows(NotBoughtTheProductException.class, () -> ratingController.rate(5.0,
                2,token));
        Assertions.assertEquals(ex.getMessage(),"You have not bought this product");
        authenticationController.logout(token);

        authenticationController.login("arya","arya",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> ratingController.rate(5.0,
                2,token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");
        authenticationController.logout(token);
        authenticationController.login("arya","arya",token);
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");
        authenticationController.logout(token);

        /** Exception Tests **/

        authenticationController.login("customer","1234",token);
        cartController.addOrChangeProduct(1,1,token);
        cartController.setAddress("home",token);
        cartController.checkout(token);
        ratingController.rate(5.0,1,token);
    }

}
