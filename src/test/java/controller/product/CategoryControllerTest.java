package controller.product;

import controller.account.Account;
import controller.category.CategoryController;
import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.ObjectAlreadyExistException;
import model.Category;
import model.Product;
import model.Role;
import model.Session;
import model.repository.CategoryRepository;
import model.repository.RepositoryContainer;
import model.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CategoryControllerTest {
    RepositoryContainer repositoryContainer;
    String token;
    CategoryController categoryController;

    @Test
    void addCategory() throws InvalidIdException, NoAccessException, ObjectAlreadyExistException, InvalidTokenException {
        setup();
        categoryController.addCategory(0, "new", "admin");
        Category category = categoryController.getByName("new");
    }

    @Test
    void removeACategory() throws InvalidIdException, InvalidTokenException, NoAccessException {
        setup();
        List<Category> categories = new ArrayList<>();
        Category category = categoryController.getCategory(19, "admin");
        categoryController.getAllCategories(0, "admin").forEach(i -> categories.add(i.clone()));
        categoryController.removeACategory(19, 0, "admin");
        Assertions.assertEquals(categoryController.getAllCategories(0, "admin"), categories.remove(category));
        /** test exception */
        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> categoryController.removeACategory(67, 0, "admin"));
        Assertions.assertEquals(ex.getMessage(), "no category exist.");
        ex = Assertions.assertThrows(NoAccessException.class, () -> categoryController.getAllCategories(18, token));
        Assertions.assertEquals(ex.getMessage(), "only manager can remove the Category.");
        ex = Assertions.assertThrows(InvalidTokenException.class, () -> categoryController.getAllCategories(98, token));
        Assertions.assertEquals(ex.getMessage(), "only manager can remove the Category.");
        /** end test*/
    }

    @BeforeEach
    void setup() {
        repositoryContainer = new RepositoryContainer();
        Session.initializeFake((UserRepository) repositoryContainer.getRepository("UserRepository"));
        token = Session.addSession();
        categoryController = new CategoryController(repositoryContainer);
    }

    @Test
    void getAllCategoriesWith() throws InvalidIdException {
        setup();
        List<Category> categoryControllerList = categoryController.getAllCategories(0, token);
        Assertions.assertEquals(categoryControllerList, repositoryContainer.getRepository("CategoryRepository").getAll());
        /** check exception*/
       Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> categoryController.getAllCategories(98,token));
       Assertions.assertEquals(ex.getMessage(),"no category exist.");
        /** end exception test*/
    }

    @Test
    void getCategory() throws InvalidIdException {
        setup();
        Category category1 = (Category) repositoryContainer.getRepository("CategoryRepository").getById(6);
        Category category = categoryController.getCategory(6, token);
        Assertions.assertEquals(category1, category);
        /** check exception*/
        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> categoryController.getAllCategories(98,token));
        Assertions.assertEquals(ex.getMessage(),"no category exist.");
        /** end exception test*/
    }

    @Test
    void removeProduct() {
        repositoryContainer = new RepositoryContainer();
        token = Session.addSession();
        categoryController = new CategoryController(repositoryContainer);
        try {
            Category category = categoryController.getCategory(6, token);
            List<Product> productList = new ArrayList<>();//TOdo
            category.getProducts().forEach(product -> productList.add(product));
            categoryController.removeProduct(6, 3, token);
            // Assertions.assertEquals(productList.);
        } catch (InvalidIdException e) {
            e.printStackTrace();
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }
    }
}
