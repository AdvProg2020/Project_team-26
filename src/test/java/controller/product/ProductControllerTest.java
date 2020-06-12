package controller.product;

import controller.account.AuthenticationController;
import exception.*;
import model.Product;
import model.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ProductRepository;
import repository.ProductSellerRepository;
import repository.RepositoryContainer;

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
    public void getProductById() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, InvalidIdException, NotLoggedINException {

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
        productController.editProduct(7,new Product(),token);
    }

    @Test
    public void addSellerTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NoAccessException, NotSellerException, NotLoggedINException {

        authenticationController.login("test5","password5",token);
        System.out.println(productSellerRepository.getById(7));
        productController.addSeller(2,productSellerRepository.getById(2),token);
        authenticationController.logout(token);
    }

    @Test
    public void getProductByNameTest() {

        Exception ex = Assertions.assertThrows(NullPointerException.class, () -> productController.getProductByName("nigga",token));
    }


}