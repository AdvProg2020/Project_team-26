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

        Assertions.assertEquals(promoController.getPromoCodeTemplateByCode("promo1", token).getPromoCode(), "promo1");

    }

    @Test
    public void getPromoCodeTemplateByIdTest() throws InvalidIdException, NotLoggedINException {

        /** Exception Tests **/
        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> promoController.getPromoCodeTemplateById(12000, token));
        Assertions.assertEquals(ex.getMessage(), "there is no promo by 12000");
        /** Exception Tests **/

        Assertions.assertEquals(promoController.getPromoCodeTemplateById(5, token).getPromoCode(), "promo2");
    }

    @Test
    public void removePromoCodeTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException, InvalidIdException, NoObjectIdException, NoAccessException, ObjectAlreadyExistException {

        /**Exception Tests **/
        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> promoController.removePromoCode(12, token));
        Assertions.assertEquals(ex.getMessage(), "You are not logged in.");

        authenticationController.login("customer", "1234", token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> promoController.removePromoCode(12, token));
        Assertions.assertEquals(ex.getMessage(), "only manager can remove the promo");
        authenticationController.logout(token);

        authenticationController.login("arya", "arya", token);
        ex = Assertions.assertThrows(InvalidIdException.class, () -> promoController.removePromoCode(1200, token));
        Assertions.assertEquals(ex.getMessage(), "there is no promo by 1200");
        /** Exception Tests **/

        String promoCode = createRandomPromo();

        Promo promo = new Promo(promoCode, null);
        promo.setStartDate(new Date(105,9,12));
        promo.setEndDate(new Date(121,5,6));
        promoController.createPromoCode(promo,token);
        Assertions.assertEquals(promoRepository.getByCode(promoCode).getPromoCode(), promoCode);
        promoController.removePromoCode(promoRepository.getByCode(promoCode).getId(), token);
        Assertions.assertEquals(promoRepository.getByCode(promoCode), null);
    }


    @Test
    public void createPromoTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, ObjectAlreadyExistException, NoAccessException, NotLoggedINException {

        /**Exception Tests **/
        authenticationController.login("arya", "arya", token);
        Promo promo = new Promo("promo1", null);
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
        authenticationController.login("arya", "arya", token);
        Exception ex = Assertions.assertThrows(InvalidDiscountPercentException.class, () -> promoController.setPercent(5, 200, token));
        Assertions.assertEquals(ex.getMessage(), "the percent can't exceed 100%");
        /** Exception Tests **/

        promoController.setPercent(5, 20, token);
        Assertions.assertEquals(promoRepository.getByCode("promo2").getPercent(), 20.0);

    }

    @Test
    public void setMaxDiscountTest() throws InvalidIdException, NoAccessException, InvalidTokenException, NotLoggedINException, InvalidFormatException, PasswordIsWrongException, InvalidAuthenticationException {

        authenticationController.login("arya", "arya", token);
        promoController.setMaxDiscount(5, 200, token);
        Assertions.assertEquals(promoRepository.getByCode("promo2").getMaxDiscount(), 200);
    }

    @Test
    public void getAllPromoCodeForCustomerTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException {

        /**Exception Tests **/
        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> promoController.getAllPromoCodeForCustomer(
                "asd", true,0,0, token));
        Assertions.assertEquals(ex.getMessage(), "you are not logged in");

        authenticationController.login("seller", "1234", token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> promoController.getAllPromoCodeForCustomer(
               null , true,0,0, token));
        Assertions.assertEquals(ex.getMessage(), "only customer");
        /**Exception Tests**/

    }

    @Test
    public void setTimeTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, InvalidIdException, NoAccessException, NotLoggedINException {

        authenticationController.login("arya", "arya", token);
        promoController.setTime(5, new Date(), "start", token);
        promoController.setTime(5, new Date(), "end", token);

    }


    @Test
    public void removeCustomerTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, InvalidIdException, NotCustomerException, NoAccessException, NotLoggedINException, ObjectAlreadyExistException {
        /** Exception Tests **/
        authenticationController.login("arya", "arya", token);
        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> promoController.removeCustomer(5, 1200, token));
        Assertions.assertEquals(ex.getMessage(), "no customer exist By 1200 id");

        ex = Assertions.assertThrows(NotCustomerException.class, () -> promoController.removeCustomer(5, 13, token));
        Assertions.assertEquals(ex.getMessage(), "You must choose a customer");

        ex = Assertions.assertThrows(InvalidIdException.class, () -> promoController.removeCustomer(5, 18, token));
        Assertions.assertEquals(ex.getMessage(), "the promo doesnt contain 18 id");
        /** Exception Tests **/

        promoController.removeCustomer(5 ,12, token);
        Assertions.assertEquals(promoRepository.getByCode("promo2").getCustomers().contains(userRepository.getUserByUsername("test5")), false);
        promoController.addCustomer(5, 12, token);

    }


    @Test
    public void addCustomerTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException {
        /** Exception Tests**/
        authenticationController.login("arya", "arya", token);
        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> promoController.addCustomer(2, 120, token));
        Assertions.assertEquals(ex.getMessage(), "no customer exists By 120 id");

    }

    private String createRandomPromo() {
        String randomPromo = "";
        randomPromo += LocalTime.now();
        return randomPromo;
    }


}
