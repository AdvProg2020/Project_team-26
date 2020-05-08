package controller.product;

import exception.InvalidIdException;
import model.Product;
import model.Session;
import model.repository.RepositoryContainer;
import org.junit.Assert;
import org.junit.*;
import org.junit.jupiter.api.Test;

class ProductControllerTest {

    String token;
    ProductController productController;
    RepositoryContainer repositoryContainer;

    @Before
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
            Assert.assertEquals(product, repositoryContainer.getRepository("ProductRepository").getById(1));
        }
        catch (InvalidIdException invalidIdException) {
            Assert.assertEquals(invalidIdException, null);
        }

        try {
            Product product = productController.getProductById(1, token);
        }
        catch (InvalidIdException invalidIdException) {
            Assert.assertEquals(invalidIdException.getMessage(), "There is no product with this id");
        }
    }

    @Test
    void editProduct() {
    }
}