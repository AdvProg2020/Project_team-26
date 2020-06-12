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

public class OrderControllerTest  {

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
    public void getASingleOrderTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NoAccessException, NoObjectIdException {

        authenticationController.login("test8","password8",token);
        orderController.getASingleOrder(1,token);
    }

    @Test
    public void getOrdersTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NoAccessException {

        authenticationController.login("test8","password8",token);
        orderController.getOrders(token);
    }

    @Test
    public void getOrdersWithFiltersTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NoAccessException, NotLoggedINException {

        authenticationController.login("test8","test8",token);
        Assertions.assertEquals(orderController.getOrdersWithFilter("sd",true,token),null);
    }
}
