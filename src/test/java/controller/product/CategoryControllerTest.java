package controller.product;

import controller.account.AuthenticationController;
import exception.*;
import model.Category;
import model.Product;
import model.Session;
import repository.CategoryRepository;
import repository.RepositoryContainer;
import repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CategoryControllerTest {
    RepositoryContainer repositoryContainer;
    String token;
    CategoryController categoryController;
    AuthenticationController authenticationController;
    CategoryRepository categoryRepository;


    @BeforeEach
    void setup() {
        repositoryContainer = new RepositoryContainer("sql");
        Session.initializeFake((UserRepository) repositoryContainer.getRepository("UserRepository"));
        token = Session.addSession();
        categoryController = new CategoryController(repositoryContainer);
        authenticationController = new AuthenticationController(repositoryContainer);
        categoryRepository = (CategoryRepository) repositoryContainer.getRepository("CategoryRepository");
    }

    @Test
    void addCategory() throws InvalidIdException, NoAccessException, ObjectAlreadyExistException, InvalidTokenException, InvalidFormatException, PasswordIsWrongException, InvalidAuthenticationException, NotLoggedINException {

        String name = createRandomName();

        /** Exception Tests **/
        authenticationController.login("test1","test1",token);
        Exception ex = Assertions.assertThrows(NoAccessException.class, () -> categoryController.addCategory(0,new Category("name"),token));
        Assertions.assertEquals(ex.getMessage(),"only manager can add category");
        authenticationController.logout(token);

        authenticationController.login("aria","aria",token);
        ex = Assertions.assertThrows(ObjectAlreadyExistException.class, () -> categoryController.addCategory(0,new Category("pens"),token));
        Assertions.assertEquals(ex.getMessage(),"the category name should be uniq and this name is already taken");

        ex = Assertions.assertThrows(InvalidIdException.class, () -> categoryController.addCategory(1222,new Category(name),token));
        Assertions.assertEquals(ex.getMessage(),"the father Category doesnt exist");
        /** Exception Tests **/

        categoryController.addCategory(0,new Category(name),token);
        Assertions.assertNotEquals(categoryRepository.getByName(name),null);

    }

    @Test
    void removeACategory() throws InvalidIdException, InvalidTokenException, NoAccessException, NoObjectIdException {
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



    @Test
    void getAllCategoriesWith() throws InvalidIdException {
        List<Category> categoryControllerList = categoryController.getAllCategories(0, token);
        Assertions.assertEquals(categoryControllerList, repositoryContainer.getRepository("CategoryRepository").getAll());
        /** check exception*/
        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> categoryController.getAllCategories(98, token));
        Assertions.assertEquals(ex.getMessage(), "no category exist.");
        /** end exception test*/
    }

    @Test
    void getCategory() throws InvalidIdException {
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

    private String createRandomName() {
        String randomName = "randomCategory";
        randomName += LocalTime.now();
        return randomName;
    }
}
