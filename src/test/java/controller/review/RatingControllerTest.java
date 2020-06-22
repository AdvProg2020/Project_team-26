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

        authenticationController.login("test5","test5",token);
        ex = Assertions.assertThrows(NotBoughtTheProductException.class, () -> ratingController.rate(5.0,
                2,token));
        Assertions.assertEquals(ex.getMessage(),"You have not bought this product");
        authenticationController.logout(token);

        authenticationController.login("aria","aria",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> ratingController.rate(5.0,
                2,token));
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");
        authenticationController.logout(token);
        authenticationController.login("aria","aria",token);
        Assertions.assertEquals(ex.getMessage(),"You are not allowed to do that.");
        authenticationController.logout(token);

        /** Exception Tests **/

        authenticationController.login("test8","test8",token);
        cartController.addOrChangeProduct(18,1,token);
        cartController.setAddress("retard abad",token);
        cartController.checkout(token);
        ratingController.rate(5.0,14,token);
        Assertions.assertEquals(rateRepository.getById(6).getScore(),5.0);
    }

}
