package controller.product;

import controller.account.AuthenticationController;
import exception.*;
import model.Product;
import model.ProductSeller;
import model.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ProductRepository;
import repository.ProductSellerRepository;
import repository.RepositoryContainer;

import java.util.List;

class ProductControllerTest {

    private String token;
    private ProductController productController;
    private RepositoryContainer repositoryContainer;
    private AuthenticationController authenticationController;
    private ProductRepository productRepository;
    private ProductSellerRepository productSellerRepository;

    @BeforeEach
    public void Setup() {
        token = Session.addSession();
        repositoryContainer = new RepositoryContainer("sql");
        productController = new ProductController(repositoryContainer);
        authenticationController = new AuthenticationController(repositoryContainer);
        productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
        productSellerRepository = (ProductSellerRepository) repositoryContainer.getRepository("ProductSellerRepository");
    }

    @Test
    public void createProductTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException {
        /** Exception Tests **/

        Exception ex = Assertions.assertThrows(NullPointerException.class, () -> productController.createProduct(null, token));
        Assertions.assertEquals(ex.getMessage(), null);

        authenticationController.login("aria", "aria", token);
        ex = Assertions.assertThrows(NotSellerException.class, () -> productController.createProduct(productRepository.getByName("paper"), token));
        Assertions.assertEquals(ex.getMessage(), "You must be seller to add product");
        authenticationController.logout(token);

        authenticationController.login("test4","test4",token);
        ex = Assertions.assertThrows(ObjectAlreadyExistException.class, () -> productController.createProduct(productRepository.getByName("paper"),token));
        Assertions.assertEquals(ex.getMessage(),"Product with this name already exists");
        /** Exception Tests **/
        authenticationController.logout(token);

    }

    @Test
    public void getProductByIdTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, InvalidIdException, NotLoggedINException {

        authenticationController.login("aria","aria",token);
        productController.getProductById(1,token);
        authenticationController.logout(token);
    }

    @Test
    public void editProductTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, InvalidIdException, NoAccessException, NotSellerException, NotLoggedINException {
        authenticationController.login("test4","test4",token);
        Exception ex = Assertions.assertThrows(NoAccessException.class, () ->productController.editProduct(2,new Product(),token));
        Assertions.assertEquals(ex.getMessage(),"You can only change your own products");
        authenticationController.logout(token);
        authenticationController.login("test1","test1",token);
        productController.editProduct(7,productRepository.getById(10),token);
    }

    @Test
    public void addSellerTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NoAccessException, NotSellerException, NotLoggedINException {

        authenticationController.login("test1","test1",token);
        productController.addSeller(2,productSellerRepository.getById(4),token);
        authenticationController.logout(token);
    }

    @Test
    public void getProductByNameTest() {
        Exception ex = Assertions.assertThrows(NoObjectIdException.class, () -> productController.getProductByName("nigga",token));
    }

    @Test
    public void getProductSellerByIdAndSellerIdTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException, InvalidIdException, NoObjectIdException, NoAccessException {
        /** Exception Tests **/
        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> productController.getProductSellerByIdAndSellerId(12,token));
        Assertions.assertEquals(ex.getMessage(),"You are not logged in.");

        authenticationController.login("aria","aria",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> productController.getProductSellerByIdAndSellerId(12,token));
        Assertions.assertEquals(ex.getMessage(),"You must be a seller to do this.");
        authenticationController.logout(token);

        authenticationController.login("test4","test4",token);
        ex = Assertions.assertThrows(NoObjectIdException.class, () -> productController.getProductSellerByIdAndSellerId(102,token));
        Assertions.assertEquals(ex.getMessage(),"The specified Object does not Exist.");

        ex = Assertions.assertThrows(NoAccessException.class, () -> productController.getProductSellerByIdAndSellerId(12,token));
        Assertions.assertEquals(ex.getMessage(),"This Product is not for you.");
        authenticationController.logout(token);

        /** Exception Tests **/

        authenticationController.login("test1","test1",token);
        Assertions.assertEquals(13,productController.getProductSellerByIdAndSellerId(12,token).getId());
    }

    @Test
    public void getAllProductWithFilterForSellerIdTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException, NoAccessException {
        /** Exception Tests **/

        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> productController.getAllProductWithFilterForSellerId(
                null,"name",true,0,0,token
        ));
        Assertions.assertEquals(ex.getMessage(),"You must be logged in to view all of your products");

        authenticationController.login("aria","aria",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> productController.getAllProductWithFilterForSellerId(
                null,"name",true,0,0,token));
        Assertions.assertEquals(ex.getMessage(),"Only a seller can view his/her products");
        authenticationController.logout(token);
        /** Exception Tests **/

        authenticationController.login("test2","test2",token);
        Assertions.assertNotEquals(null,productController.getAllProductWithFilterForSellerId(
                null,"name",true,0,0,token));
    }

    @Test
    public void removeProductTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException, NoAccessException, InvalidIdException {

        /** Exception Tests **/
        Exception ex = Assertions.assertThrows(NotLoggedINException.class, () -> productController.removeProduct(2,token));
        Assertions.assertEquals(ex.getMessage(),"You are not Logged in.");

        authenticationController.login("test5","test5",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> productController.removeProduct(2,token));
        Assertions.assertEquals(ex.getMessage(),"You must be a seller|manager to remove a product");
        authenticationController.logout(token);

        authenticationController.login("test4","test4",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> productController.removeProduct(14,token));
        Assertions.assertEquals(ex.getMessage(),"You don't have this item for sale.");
        authenticationController.logout(token);

        /** Exception Tests **/

        authenticationController.login("test3","test3",token);
        productController.removeProduct(14,token);

    }

    @Test
    public void editProductSellerTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException {

        /** Exception Tests **/
        authenticationController.login("test4","test4",token);
        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> productController.editProductSeller(1200,new ProductSeller(),token));
        Assertions.assertEquals(ex.getMessage(),"There is no productSeller with this id to change");
        authenticationController.logout(token);

        authenticationController.login("aria","aria",token);
        ex = Assertions.assertThrows(NotSellerException.class, () -> productController.editProductSeller(14,new ProductSeller(),token));
        Assertions.assertEquals(ex.getMessage(),"You must be seller to edit productSeller");
        authenticationController.logout(token);

        authenticationController.login("test3","test3",token);
        ex = Assertions.assertThrows(NoAccessException.class, () -> productController.editProductSeller(3,new ProductSeller(),token));
        Assertions.assertEquals(ex.getMessage(),"You can only change your own products");
        /**Exception Tests **/

    }

    @Test
    public void getAllProductWithFilterTest() {
       List<Product> list = productController.getAllProductWithFilter(null,"name",true,0,0,token);
    }


}