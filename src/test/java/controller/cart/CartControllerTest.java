package controller.cart;

import controller.account.AuthenticationController;
import controller.discount.PromoController;
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

public class CartControllerTest {

    private RepositoryContainer repositoryContainer;
    private UserRepository userRepository;
    private ProductSellerRepository productSellerRepository;
    private PromoRepository promoRepository;
    private String token;
    private AuthenticationController authenticationController;
    private PromoController promoController;
    private CartController cartController;

    @BeforeEach
    public void setup() {
        repositoryContainer = new RepositoryContainer("sql");
        token = Session.addSession();
        authenticationController = new AuthenticationController(repositoryContainer);
        promoController = new PromoController(repositoryContainer);
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
    public void userPromoCodeTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException, PromoNotAvailableException, NoAccessException, InvalidPromoCodeException, InvalidIdException, NotCustomerException, ObjectAlreadyExistException {
        /** Exception Tests **/

        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> cartController.usePromoCode(
                "as", token));
        Assertions.assertEquals(ex.getMessage(), "You must login before using promo code.");

        authenticationController.login("arya", "arya", token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> cartController.usePromoCode("as", token));
        Assertions.assertEquals(ex.getMessage(), "You must be a customer to use promo code.");
        authenticationController.logout(token);

        authenticationController.login("customer", "1234", token);
        ex = Assertions.assertThrows(InvalidPromoCodeException.class, () -> cartController.usePromoCode("randomPromoIMade",
                token));
        Assertions.assertEquals(ex.getMessage(), "There is no promo with this code.");

        ex = Assertions.assertThrows(PromoNotAvailableException.class, () -> cartController.usePromoCode("promo1",
                token));
        Assertions.assertEquals(ex.getMessage(), "This promo is not for you.");
        authenticationController.logout(token);

        /** Exception Tests **/

        authenticationController.login("customer", "1234", token);
        cartController.usePromoCode("promo2", token);
        Assertions.assertEquals(cartController.getCart(token).getUsedPromo().getPromoCode(), "promo2");

    }

    @Test
    public void addOrChangeProduct() throws InvalidIdException, NotEnoughProductsException, InvalidTokenException, NoSuchObjectException, exception.NoSuchObjectException {
        /** Exception Tests **/

        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> cartController.addOrChangeProduct(
                1200, 2, token));
        Assertions.assertEquals(ex.getMessage(), "There is no product seller with this id");

        ex = Assertions.assertThrows(InvalidIdException.class, () -> cartController.addOrChangeProduct(
                13000, 1, token));
        Assertions.assertEquals(ex.getMessage(), "There is no product seller with this id");

        ex = Assertions.assertThrows(NoSuchObjectException.class, () -> cartController.getAmountInCartBySellerId(
                50000, token));
        Assertions.assertEquals(ex.getMessage(), "No Object with specified details exist");

        /** Exception Tests **/


        cartController.addOrChangeProduct(1, 1, token);
        Assertions.assertEquals(cartController.getAmountInCartBySellerId(1, token), 1);

        Assertions.assertEquals(cartController.getTotalPrice(cartController.getCart(token), token), 20);

    }

    @Test
    public void checkoutTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException, InvalidIdException, NotEnoughProductsException, NoAccessException, NotEnoughCreditException {

        /** Exception tests **/

        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> cartController.checkout(token));
        Assertions.assertEquals(ex.getMessage(), "You must login before using promo code.");

        authenticationController.login("arya", "arya", token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> cartController.checkout(token));
        Assertions.assertEquals(ex.getMessage(),"You must be a customer to be able to buy.");
        authenticationController.logout(token);

        authenticationController.login("randomName18:52:21.241303600","124",token);
        cartController.addOrChangeProduct(1,1,token);
        ex = Assertions.assertThrows(NotEnoughCreditException.class,() -> cartController.checkout(token));
        Assertions.assertEquals(ex.getMessage(),"You don't have enough creadit to pay 20");
        authenticationController.logout(token);
        /** Exception Tests **/

        authenticationController.login("customer","1234",token);
        cartController.addOrChangeProduct(1,1,token);
        cartController.checkout(token);


    }



}
