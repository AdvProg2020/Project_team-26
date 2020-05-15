package controller.cart;

import controller.account.AuthenticationController;
import exception.InvalidTokenException;
import model.ProductSeller;
import model.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import repository.ProductSellerRepository;
import repository.RepositoryContainer;
import repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;

public class CartControllerTest {

    private RepositoryContainer repositoryContainer;
    private UserRepository userRepository;
    private ProductSellerRepository productSellerRepository;
    private String token;
    private AuthenticationController authenticationController;
    private CartController cartController;

    @BeforeEach
    public void setup() {
        repositoryContainer = new RepositoryContainer();
        token = Session.addSession();
        authenticationController = new AuthenticationController(repositoryContainer);
        cartController = new CartController(repositoryContainer);
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
        productSellerRepository = (ProductSellerRepository) repositoryContainer.getRepository("ProductSellerRepository");
    }

    @Test
    public void setAddressTest() throws InvalidTokenException {
        cartController.setAddress("Nigga",token);
        Assertions.assertEquals(cartController.showCart(token).getAddress(),"Nigga");
    }








}
