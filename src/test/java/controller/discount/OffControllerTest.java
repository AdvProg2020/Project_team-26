package controller.discount;

import antlr.ASdebug.ASDebugStream;
import com.sun.xml.bind.v2.TODO;
import controller.account.AuthenticationController;
import controller.cart.CartController;
import exception.*;
import model.Off;
import model.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.*;

import javax.print.DocFlavor;

public class OffControllerTest {

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
    }


    @Test
    public void createNewOffTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException, NoAccessException {
        /** Exception Tests **/
        Exception ex = Assertions.assertThrows(NotLoggedINException.class,() -> offController.createNewOff(new Off("pp"),token));
        Assertions.assertEquals(ex.getMessage(),"You must be logged in.");

        authenticationController.login("aria","aria",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> offController.createNewOff(new Off("pp"),token));
        Assertions.assertEquals(ex.getMessage(),"seller can create a off");
        authenticationController.logout(token);

        /**Exception Tests **/

        authenticationController.login("test4","test4",token);
        offController.createNewOff(new Off("pp"),token);

    }


    @Test
    public void addProductToOffTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, ObjectAlreadyExistException, NoAccessException, NotLoggedINException, InvalidIdException {

        /**Exception Tests **/
        authenticationController.login("test2","test2",token);
        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> offController.addProductToOff(
                offRepository.getById(2),200,300,20,false,token));
        Assertions.assertEquals(ex.getMessage(),"No Such product Exists");
        /** Exception Tests **/

        offController.addProductToOff(offRepository.getById(2),11,300,20,false,token);
    }


    @Test
    public void getOffByTest() throws InvalidIdException {

        /** Exception Tests **/
        Exception ex = Assertions.assertThrows(InvalidIdException.class,() -> offController.getOff(120, token));
        Assertions.assertEquals(ex.getMessage(),"the off with 120 doesn't exist");
        /** Exception Tests **/

        offController.getOff(1,token);
    }

    @Test
    public void editTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, InvalidIdException, NoAccessException, NotLoggedINException {

        authenticationController.login("test4","test4",token);
        Exception ex = Assertions.assertThrows(NoAccessException.class,() ->offController.edit(new Off("sd"),2,token));
        Assertions.assertEquals(ex.getMessage(),"you can only change your off");

    }


    @Test
    public void removeProductFromOffTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, InvalidIdException, NoAccessException, NotLoggedINException {

        /** Exception Tests **/
        authenticationController.login("test4","test4",token);
        Exception ex = Assertions.assertThrows(ObjectAlreadyExistException.class, () -> offController.removeProductFromOff(
                offRepository.getById(2),2,false,token));
        /** Exception Tests **/
        offController.removeAOff(2,token);
    }




}
