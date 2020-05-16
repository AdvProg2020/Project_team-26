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
    public void removePromoCodeTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException, InvalidIdException, NoObjectIdException, NoAccessException {

        /**Exception Tests **/
        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> promoController.removePromoCode(12,token));
        Assertions.assertEquals(ex.getMessage(),"You are not logged in.");

        authenticationController.login("test8","password8",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> promoController.removePromoCode(12,token));
        Assertions.assertEquals(ex.getMessage(),"only manager can remove the promo");
        authenticationController.logout(token);

        authenticationController.login("test1","password1",token);
        ex = Assertions.assertThrows(InvalidIdException.class, () -> promoController.removePromoCode(120,token));
        Assertions.assertEquals(ex.getMessage(),"there is no promo by 120");
        /** Exception Tests **/

        Assertions.assertEquals(promoRepository.getByCode("Promo0").getPromoCode(),"Promo0");
        promoController.removePromoCode(6,token);
        Assertions.assertEquals(promoRepository.getByCode("Promo0"),null);
    }


    @Test
    public void createPromoTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, ObjectAlreadyExistException, NoAccessException, NotLoggedINException {

        /**Exception Tests **/
        authenticationController.login("test1","password1",token);
        Promo promo = new Promo("Promo0",null);
        Promo promo2 = new Promo("Promo200",null);
        Exception ex = Assertions.assertThrows(ObjectAlreadyExistException.class, () -> promoController.createPromoCode(promo,token));
        /**Exception Tests **/

        promoController.createPromoCode(promo2,token);
        Assertions.assertEquals(promoRepository.getByCode("Promo200").getPromoCode(),"Promo200");
    }

    @Test
    public void setPercentTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, InvalidIdException, NoAccessException, NotLoggedINException, InvalidDiscountPercentException {

        /** Exception Tests **/
        authenticationController.login("test1","password1",token);
        Exception ex = Assertions.assertThrows(InvalidDiscountPercentException.class, () -> promoController.setPercent(6,200,token));
        Assertions.assertEquals(ex.getMessage(),"the percent can't exceed 100%");
        /** Exception Tests **/

        promoController.setPercent(6,20,token);
        Assertions.assertEquals(promoRepository.getByCode("Promo0").getPercent(),20.0);

    }

    @Test
    public void setMaxDiscountTest() throws InvalidIdException, NoAccessException, InvalidTokenException, NotLoggedINException, InvalidFormatException, PasswordIsWrongException, InvalidAuthenticationException {

        authenticationController.login("test1","password1",token);
        promoController.setMaxDiscount(6,200,token);
        Assertions.assertEquals(promoRepository.getByCode("Promo0").getMaxDiscount(),200);
    }

    @Test
    public void getAllPromoCodeForCustomerTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException {

        /**Exception Tests **/
        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> promoController.getAllPromoCodeForCustomer(
                "asd",true,token));
        Assertions.assertEquals(ex.getMessage(),"you are not logged in");

        authenticationController.login("test5","password5",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> promoController.getAllPromoCodeForCustomer(
                "asd",true,token));
        Assertions.assertEquals(ex.getMessage(),"only customer");
        /**Exception Tests**/

    }

    @Test
    public void setTimeTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, InvalidIdException, NoAccessException, NotLoggedINException {

        authenticationController.login("test1","password1",token);
        promoController.setTime(6, new Date(),"start",token);
        promoController.setTime(7,new Date(), "end",token);

    }


    @Test
    public void removeCustomerTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, InvalidIdException, NotCustomerException, NoAccessException, NotLoggedINException {
        /** Exception Tests **/
        authenticationController.login("test1","password1",token);
        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> promoController.removeCustomer(6,120,token));
        Assertions.assertEquals(ex.getMessage(),"no customer exist By 120 id");

        ex = Assertions.assertThrows(NotCustomerException.class, () -> promoController.removeCustomer(6,1,token));
        Assertions.assertEquals(ex.getMessage(),"You must choose a customer");

        ex = Assertions.assertThrows(InvalidIdException.class, () -> promoController.removeCustomer(6,9,token));
        Assertions.assertEquals(ex.getMessage(),"the promo doesnt contain 9 id");
        /** Exception Tests **/


        promoController.removeCustomer(6,8,token);
        Assertions.assertEquals(promoRepository.getByCode("Promo0").getCustomers().contains(userRepository.getUserByUsername("test8")),false);
        
    }



}
