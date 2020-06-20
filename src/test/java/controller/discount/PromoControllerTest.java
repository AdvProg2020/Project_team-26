package controller.discount;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import controller.account.AuthenticationController;
import controller.cart.CartController;
import exception.*;
import model.Customer;
import model.Promo;
import model.Session;
import org.hibernate.boot.model.source.internal.hbm.AbstractPluralAssociationElementSourceImpl;
import org.hibernate.exception.spi.AbstractSQLExceptionConversionDelegate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ProductSellerRepository;
import repository.PromoRepository;
import repository.RepositoryContainer;
import repository.UserRepository;

import java.time.LocalTime;
import java.util.Date;

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
        repositoryContainer = new RepositoryContainer("sql");
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
        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> promoController.getPromoCodeTemplateByCode("base dg", token));
        Assertions.assertEquals(ex.getMessage(), "there is no promo by base dg");
        /** Exception Tests **/

        Assertions.assertEquals(promoController.getPromoCodeTemplateByCode("randomForLogin2026a1", token).getPromoCode(), "randomForLogin2026a1");

    }

    @Test
    public void getPromoCodeTemplateByIdTest() throws InvalidIdException, NotLoggedINException {

        /** Exception Tests **/
        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> promoController.getPromoCodeTemplateById(12000, token));
        Assertions.assertEquals(ex.getMessage(), "there is no promo by 12000");
        /** Exception Tests **/

        Assertions.assertEquals(promoController.getPromoCodeTemplateById(22, token).getPromoCode(), "randomForLogin2026a1");
    }

    @Test
    public void removePromoCodeTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException, InvalidIdException, NoObjectIdException, NoAccessException {

        /**Exception Tests **/
        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> promoController.removePromoCode(12, token));
        Assertions.assertEquals(ex.getMessage(), "You are not logged in.");

        authenticationController.login("test5", "test5", token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> promoController.removePromoCode(12, token));
        Assertions.assertEquals(ex.getMessage(), "only manager can remove the promo");
        authenticationController.logout(token);

        authenticationController.login("aria", "aria", token);
        ex = Assertions.assertThrows(InvalidIdException.class, () -> promoController.removePromoCode(1200, token));
        Assertions.assertEquals(ex.getMessage(), "there is no promo by 1200");
        /** Exception Tests **/

        Assertions.assertEquals(promoRepository.getByCode("randomForLogin2023a9").getPromoCode(), "randomForLogin2023a9");
        promoController.removePromoCode(promoRepository.getByCode("randomForLogin2023a9").getId(), token);
        Assertions.assertEquals(promoRepository.getByCode("randomForLogin2023a9"), null);
    }


    @Test
    public void createPromoTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, ObjectAlreadyExistException, NoAccessException, NotLoggedINException {

        /**Exception Tests **/
        authenticationController.login("aria", "aria", token);
        Promo promo = new Promo("randomForLogin2026a3", null);
        Promo promo2 = new Promo(createRandomPromo(), null);
        promo2.setStartDate(new Date(105,9,12));
        promo2.setEndDate(new Date(121,5,6));
        Exception ex = Assertions.assertThrows(ObjectAlreadyExistException.class, () -> promoController.createPromoCode(promo, token));
        /**Exception Tests **/

        promoController.createPromoCode(promo2, token);
        Assertions.assertEquals(promo2.getPromoCode(),promo2.getPromoCode());
    }

    @Test
    public void setPercentTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, InvalidIdException, NoAccessException, NotLoggedINException, InvalidDiscountPercentException {

        /** Exception Tests **/
        authenticationController.login("aria", "aria", token);
        Exception ex = Assertions.assertThrows(InvalidDiscountPercentException.class, () -> promoController.setPercent(22, 200, token));
        Assertions.assertEquals(ex.getMessage(), "the percent can't exceed 100%");
        /** Exception Tests **/

        promoController.setPercent(22, 20, token);
        Assertions.assertEquals(promoRepository.getByCode("randomForLogin2026a1").getPercent(), 20.0);

    }

    @Test
    public void setMaxDiscountTest() throws InvalidIdException, NoAccessException, InvalidTokenException, NotLoggedINException, InvalidFormatException, PasswordIsWrongException, InvalidAuthenticationException {

        authenticationController.login("aria", "aria", token);
        promoController.setMaxDiscount(22, 200, token);
        Assertions.assertEquals(promoRepository.getByCode("randomForLogin2026a1").getMaxDiscount(), 200);
    }

    @Test
    public void getAllPromoCodeForCustomerTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException {

        /**Exception Tests **/
        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> promoController.getAllPromoCodeForCustomer(
                "asd", true,0,0, token));
        Assertions.assertEquals(ex.getMessage(), "you are not logged in");

        authenticationController.login("test4", "test4", token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> promoController.getAllPromoCodeForCustomer(
               null , true,0,0, token));
        Assertions.assertEquals(ex.getMessage(), "only customer");
        /**Exception Tests**/

    }

    @Test
    public void setTimeTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, InvalidIdException, NoAccessException, NotLoggedINException {

        authenticationController.login("aria", "aria", token);
        promoController.setTime(22, new Date(), "start", token);
        promoController.setTime(22, new Date(), "end", token);

    }


    @Test
    public void removeCustomerTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, InvalidIdException, NotCustomerException, NoAccessException, NotLoggedINException, ObjectAlreadyExistException {
        /** Exception Tests **/
        authenticationController.login("aria", "aria", token);
        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> promoController.removeCustomer(22, 120, token));
        Assertions.assertEquals(ex.getMessage(), "no customer exist By 120 id");

        ex = Assertions.assertThrows(NotCustomerException.class, () -> promoController.removeCustomer(22, 28, token));
        Assertions.assertEquals(ex.getMessage(), "You must choose a customer");

        ex = Assertions.assertThrows(InvalidIdException.class, () -> promoController.removeCustomer(22, 60, token));
        Assertions.assertEquals(ex.getMessage(), "the promo doesnt contain 60 id");
        /** Exception Tests **/

        promoController.addCustomer(22, 33, token);
        promoController.removeCustomer(22 ,33, token);
        Assertions.assertEquals(promoRepository.getByCode("randomForLogin2026a1").getCustomers().contains(userRepository.getUserByUsername("test5")), false);

    }


    @Test
    public void addCustomerTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException {
        /** Exception Tests**/
        authenticationController.login("aria", "aria", token);
        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> promoController.addCustomer(22, 120, token));
        Assertions.assertEquals(ex.getMessage(), "no customer exists By 120 id");

    }

    private String createRandomPromo() {
        String randomPromo = "";
        randomPromo += LocalTime.now();
        return randomPromo;
    }


}
