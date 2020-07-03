package controller.order;

import controller.account.AuthenticationController;
import controller.cart.CartController;
import controller.discount.OffController;
import exception.*;
import model.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.*;

import java.util.ArrayList;

public class OrderControllerTest {

    private RepositoryContainer repositoryContainer;
    private UserRepository userRepository;
    private ProductSellerRepository productSellerRepository;
    private ProductRepository productRepository;
    private PromoRepository promoRepository;
    private String token;
    private AuthenticationController authenticationController;
    private CartController cartController;
    private OffController offController;
    private OffRepository offRepository;
    private OrderController orderController;

    @BeforeEach
    public void setup() {
        repositoryContainer = new RepositoryContainer("sql");
        token = Session.addSession();
        authenticationController = new AuthenticationController(repositoryContainer);
        cartController = new CartController(repositoryContainer);
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
        productSellerRepository = (ProductSellerRepository) repositoryContainer.getRepository("ProductSellerRepository");
        promoRepository = (PromoRepository) repositoryContainer.getRepository("PromoRepository");
        offController = new OffController(repositoryContainer);
        productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
        offRepository = (OffRepository) repositoryContainer.getRepository("OffRepository");
        orderController = new OrderController(repositoryContainer);
    }


    @Test
    public void getASingleOrderTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NoAccessException, NoObjectIdException, NotLoggedINException {

        authenticationController.login("customer", "1234", token);
        Exception ex = Assertions.assertThrows(NoObjectIdException.class, () -> orderController.getASingleOrder(100, token));
        Assertions.assertEquals(ex.getMessage(), "Object does not exist.");
        authenticationController.logout(token);

        authenticationController.login("customer","1234",token);
        Assertions.assertNotEquals(null,orderController.getASingleOrder(1,token));
    }

    @Test
    public void getOrdersTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NoAccessException {

        authenticationController.login("customer", "1234", token);
        orderController.getOrders(token);
    }

    @Test
    public void getOrdersWithFiltersTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NoAccessException, NotLoggedINException {
        authenticationController.login("test1", "1234", token);
        Assertions.assertEquals(orderController.getOrdersWithFilter(null, true, 0, 0, token), new ArrayList<>());
    }

    @Test
    public void getOrdersWithFilterAndPageTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException, NoAccessException {

        /**Exception Tests **/

        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> orderController.getOrdersWithFilter(null, true, 0, 0, token));
        Assertions.assertEquals(ex.getMessage(), "You must be logged in");

        authenticationController.login("arya", "arya", token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> orderController.getOrdersWithFilter(null, true, 0, 0, token));
        Assertions.assertEquals(ex.getMessage(), "You must be a customer to gte Orders");
        authenticationController.logout(token);

        /** Exception Tests **/

        authenticationController.login("test1", "1234", token);
        Assertions.assertEquals(new ArrayList<>(), orderController.getOrdersWithFilter(null, true, 0, 0, token));

    }

    @Test
    public void getProductBuyerByProductIdTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException, NoAccessException, InvalidIdException {

        /** Exception Tests **/

        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> orderController.getProductBuyerByProductId(2, token));
        Assertions.assertEquals(ex.getMessage(), "You must be Logged in to do this.");

        authenticationController.login("arya", "arya", token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> orderController.getProductBuyerByProductId(12, token));
        Assertions.assertEquals(ex.getMessage(), "You must be a seller to view buyers of a product.");
        authenticationController.logout(token);

        authenticationController.login("seller", "1234", token);
        ex = Assertions.assertThrows(InvalidIdException.class, () -> orderController.getProductBuyerByProductId(2000,token));
        Assertions.assertEquals(ex.getMessage(),"The specified product does not exist.");

        ex = Assertions.assertThrows(NoAccessException.class, () -> orderController.getProductBuyerByProductId(1,token));
        Assertions.assertEquals(ex.getMessage(),"You don't own this product.");
        authenticationController.logout(token);

        /** Exception Tests **/

        authenticationController.login("seller2","1234",token);
        Assertions.assertNotEquals(null,orderController.getProductBuyerByProductId(1,token));

    }


}
