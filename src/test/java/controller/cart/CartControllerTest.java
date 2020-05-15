package controller.cart;

import controller.account.AuthenticationController;
import exception.*;
import model.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ProductSellerRepository;
import repository.RepositoryContainer;
import repository.UserRepository;

import java.io.DataOutput;

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
        cartController.setAddress("Nigga", token);
        Assertions.assertEquals(cartController.showCart(token).getAddress(), "Nigga");
    }

    @Test
    public void userPromoCodeTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException, PromoNotAvailableException, NoAccessException, InvalidPromoCodeException {
        /** Exception Tests **/

        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> cartController.usePromoCode(
                "as", token));
        Assertions.assertEquals(ex.getMessage(), "You must login before using promo code.");

        authenticationController.login("test1", "password1", token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> cartController.usePromoCode("as",token));
        Assertions.assertEquals(ex.getMessage(),"You must be a customer to use promo code.");
        authenticationController.logout(token);

        authenticationController.login("test9","password9", token);
        ex = Assertions.assertThrows(InvalidPromoCodeException.class, () -> cartController.usePromoCode("asd",
                token));
        Assertions.assertEquals(ex.getMessage(),"There is no promo with this code.");

        ex = Assertions.assertThrows(PromoNotAvailableException.class, () -> cartController.usePromoCode("Promo1",
                token));
        Assertions.assertEquals(ex.getMessage(),"This promo is not for you.");
        authenticationController.logout(token);

        /** Exception Tests **/

        authenticationController.login("test8","password8",token);
        cartController.usePromoCode("Promo1",token);
        Assertions.assertEquals(cartController.showCart(token).getUsedPromo().getPromoCode(),"Promo1");





    }


}
