package controller.product;

import exception.*;
import model.Category;
import model.Product;
import model.Session;
import repository.RepositoryContainer;
import repository.UserRepository;
import org.junit.jupiter.api.Assertions;
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
        Category newcategory = new Category("new");
        categoryController.addCategory(0, newcategory, "admin");
        Category category = categoryController.getByName("new");
    }

    @Test
    void removeACategory() throws InvalidIdException, InvalidTokenException, NoAccessException, NoObjectIdException {
        setup();
        List<Category> categories = new ArrayList<>();
        Category category = categoryController.getCategory(6, "admin");
        categoryController.getAllCategories(0, "admin").forEach(i -> categories.add(i.clone()));
        categoryController.removeACategory(7, 0, "admin");
        List<Integer> ids = new ArrayList<>();
        List<Integer> afterids = new ArrayList<>();
        categoryController.getAllCategories(0, "admin").forEach(i -> ids.add(i.getId()));
        categories.remove(category);
        categories.forEach(i -> afterids.add(i.getId()));
        Assertions.assertEquals(ids, afterids);
        /** test exception */
        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> categoryController.removeACategory(67, 0, "admin"));
        Assertions.assertEquals(ex.getMessage(), "no category exist.");
//       /** end test*/
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
        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> categoryController.getAllCategories(98, token));
        Assertions.assertEquals(ex.getMessage(), "no category exist.");
        /** end exception test*/
    }

    @Test
    void getCategory() throws InvalidIdException {
        setup();
        Category category1 = (Category) repositoryContainer.getRepository("CategoryRepository").getById(16);
        Category category = categoryController.getCategory(16, token);
        Assertions.assertEquals(category1.getId(), category.getId());
        /** check exception*/
        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> categoryController.getAllCategories(98, token));
        Assertions.assertEquals(ex.getMessage(), "no category exist.");
        /** end exception test*/
    }

    @Test
    void removeProduct() {
        setup();
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
