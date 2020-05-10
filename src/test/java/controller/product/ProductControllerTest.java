package controller.product;

import controller.ProductController;
import exception.InvalidIdException;
import model.Product;
import model.Session;
import model.repository.RepositoryContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ProductControllerTest {

    String token;
    ProductController productController;
    RepositoryContainer repositoryContainer;

    @BeforeAll
    void setup() {

    }

    @Test
    void createProduct() {
    }

    @Test
    void addSeller() {
    }

    @Test
    void getProductById() {
        repositoryContainer = new RepositoryContainer();
        token = Session.addSession();
        productController = new ProductController(repositoryContainer);
        try {
            Product product = productController.getProductById(1, token);
            Assertions.assertEquals(product, repositoryContainer.getRepository("ProductRepository").getById(1));
        }
        catch (InvalidIdException invalidIdException) {
            Assertions.assertEquals(invalidIdException, null);
        }

        try {
            Product product = productController.getProductById(1, token);
        }
        catch (InvalidIdException invalidIdException) {
            Assertions.assertEquals(invalidIdException.getMessage(), "There is no product with this id");
        }
    }

    @Test
    void editProduct() {
    }
}