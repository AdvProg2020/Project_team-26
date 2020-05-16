package controller.discount;

import controller.account.AuthenticationController;
import controller.cart.CartController;
import exception.InvalidIdException;
import exception.NotLoggedINException;
import model.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ProductSellerRepository;
import repository.PromoRepository;
import repository.RepositoryContainer;
import repository.UserRepository;

public class PromoControllerTest {

    private RepositoryContainer repositoryContainer;
    private UserRepository userRepository;
    private ProductSellerRepository productSellerRepository;
    private PromoRepository promoRepository;
    private String token;
    private AuthenticationController authenticationController;
    private CartController cartController;
    private PromoController promoController;

    @BeforeEach
    public void setup() {
        repositoryContainer = new RepositoryContainer();
        token = Session.addSession();
        authenticationController = new AuthenticationController(repositoryContainer);
        cartController = new CartController(repositoryContainer);
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
        productSellerRepository = (ProductSellerRepository) repositoryContainer.getRepository("ProductSellerRepository");
        promoRepository = (PromoRepository) repositoryContainer.getRepository("PromoRepository");
        promoController = new PromoController(repositoryContainer);
    }


    @Test
    public void getPromoCodeTemplateByCodeTest() throws InvalidIdException, NotLoggedINException {

        /** Exception Tests **/
        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> promoController.getPromoCodeTemplateByCode("base dg",token));
        Assertions.assertEquals(ex.getMessage(),"there is no promo by base dg");
        /** Exception Tests **/

        Assertions.assertEquals(promoController.getPromoCodeTemplateByCode("Promo1",token).getPromoCode(),"Promo1");

    }

    @Test
    public void getPromoCodeTemplateByIdTest() throws InvalidIdException, NotLoggedINException {

        /** Exception Tests **/
        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> promoController.getPromoCodeTemplateById(120,token));
        Assertions.assertEquals(ex.getMessage(),"there is no promo by 120");
        /** Exception Tests **/

        Assertions.assertEquals(promoController.getPromoCodeTemplateById(6,token).getPromoCode(),"Promo0");
    }

    @Test
    public void removePromoCodeTest() {

        /**Exception Tests **/
        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> promoController.removePromoCode(12,token));
        Assertions.assertEquals(ex.getMessage(),"You are not logged in.");
    }


}
