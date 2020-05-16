package controller.cart;

import controller.account.AuthenticationController;
import exception.*;
import model.Customer;
import model.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ProductSellerRepository;
import repository.PromoRepository;
import repository.RepositoryContainer;
import repository.UserRepository;

import java.rmi.NoSuchObjectException;

public class CartControllerTest {

    private RepositoryContainer repositoryContainer;
    private UserRepository userRepository;
    private ProductSellerRepository productSellerRepository;
    private PromoRepository promoRepository;
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
        promoRepository = (PromoRepository) repositoryContainer.getRepository("PromoRepository");
    }

    @Test
    public void setAddressTest() throws InvalidTokenException {
        cartController.setAddress("Nigga", token);
        Assertions.assertEquals(cartController.getCart(token).getAddress(), "Nigga");
    }

    @Test
    public void userPromoCodeTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException, PromoNotAvailableException, NoAccessException, InvalidPromoCodeException {
        /** Exception Tests **/

        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> cartController.usePromoCode(
                "as", token));
        Assertions.assertEquals(ex.getMessage(), "You must login before using promo code.");

        authenticationController.login("test1", "password1", token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> cartController.usePromoCode("as", token));
        Assertions.assertEquals(ex.getMessage(), "You must be a customer to use promo code.");
        authenticationController.logout(token);

        authenticationController.login("test9", "password9", token);
        ex = Assertions.assertThrows(InvalidPromoCodeException.class, () -> cartController.usePromoCode("asd",
                token));
        Assertions.assertEquals(ex.getMessage(), "There is no promo with this code.");

        ex = Assertions.assertThrows(PromoNotAvailableException.class, () -> cartController.usePromoCode("Promo1",
                token));
        Assertions.assertEquals(ex.getMessage(), "This promo is not for you.");
        authenticationController.logout(token);

        /** Exception Tests **/

        ((Customer) userRepository.getUserByUsername("test8")).addPromo(promoRepository.getByCode("Promo1"));
        authenticationController.login("test8", "password8", token);
        cartController.usePromoCode("Promo1", token);
        Assertions.assertEquals(cartController.getCart(token).getUsedPromo().getPromoCode(), "Promo1");

    }

    @Test
    public void addOrChangeProduct() throws InvalidIdException, NotEnoughProductsException, InvalidTokenException, NoSuchObjectException {
        /** Exception Tests **/

        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> cartController.addOrChangeProduct(
                12, 2, token));
        Assertions.assertEquals(ex.getMessage(), "There is no product seller with this id");

        ex = Assertions.assertThrows(NotEnoughProductsException.class, () -> cartController.addOrChangeProduct(
                5, 1, token));
        Assertions.assertEquals(ex.getMessage(), "There isn't enough products available");

        ex = Assertions.assertThrows(NoSuchObjectException.class, () -> cartController.getAmountInCartBySellerId(
                5, token));
        Assertions.assertEquals(ex.getMessage(), "No Object with specified details exist");

        /** Exception Tests **/


        cartController.addOrChangeProduct(6, 1, token);
        Assertions.assertEquals(cartController.getAmountInCartBySellerId(6, token), 1);

        Assertions.assertEquals(cartController.getToTalPrice(cartController.getCart(token), token), 12);

    }

    @Test
    public void checkoutTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException, InvalidIdException, NotEnoughProductsException {

        /** Exception tests **/

        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> cartController.checkout(token));
        Assertions.assertEquals(ex.getMessage(), "You must login before using promo code.");

        authenticationController.login("test1", "password1", token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> cartController.checkout(token));
        Assertions.assertEquals(ex.getMessage(),"You must be a customer to be able to buy.");
        authenticationController.logout(token);

        authenticationController.login("test8","password8",token);
        cartController.addOrChangeProduct(6,1,token);
        ex = Assertions.assertThrows(NotEnoughCreditException.class,() -> cartController.checkout(token));
        Assertions.assertEquals(ex.getMessage(),"You don't have enough creadit to pay 12");


    }


}
