package controller.product;

import controller.account.AuthenticationController;
import exception.*;
import model.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ProductRepository;
import repository.RepositoryContainer;

class ProductControllerTest {

    private String token;
    private ProductController productController;
    private RepositoryContainer repositoryContainer;
    private AuthenticationController authenticationController;
    private ProductRepository productRepository;

    @BeforeEach
    public void Setup() {
        token = Session.addSession();
        repositoryContainer = new RepositoryContainer();
        productController = new ProductController(repositoryContainer);
        authenticationController = new AuthenticationController(repositoryContainer);
        productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
    }

    @Test
    public void createProductTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException {
        /** Exception Tests **/

        Exception ex = Assertions.assertThrows(NullPointerException.class, () -> productController.createProduct(null, token));
        Assertions.assertEquals(ex.getMessage(), null);

        authenticationController.login("test1", "password1", token);
        ex = Assertions.assertThrows(NotSellerException.class, () -> productController.createProduct(productRepository.getByName("6"), token));
        Assertions.assertEquals(ex.getMessage(), "You must be seller to add product");
        authenticationController.logout(token);

        authenticationController.login("test6","password6",token);
        ex = Assertions.assertThrows(ObjectAlreadyExistException.class, () -> productController.createProduct(productRepository.getByName("6"),token));
        Assertions.assertEquals(ex.getMessage(),"Product with this name already exists");
        /** Exception Tests **/

    }


}