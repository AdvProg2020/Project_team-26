package controller.product;

import antlr.ASdebug.ASDebugStream;
import controller.account.AuthenticationController;
import exception.*;
import model.Category;
import model.Product;
import model.Session;
import org.hibernate.boot.spi.InFlightMetadataCollector;
import repository.CategoryRepository;
import repository.RepositoryContainer;
import repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
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
        authenticationController.login("test1", "test1", token);
        Exception ex = Assertions.assertThrows(NoAccessException.class, () -> categoryController.addCategory(0, new Category("name"), token));
        Assertions.assertEquals(ex.getMessage(), "only manager can add category");
        authenticationController.logout(token);

        authenticationController.login("aria", "aria", token);
        ex = Assertions.assertThrows(ObjectAlreadyExistException.class, () -> categoryController.addCategory(0, new Category("pens"), token));
        Assertions.assertEquals(ex.getMessage(), "the category name should be uniq and this name is already taken");

        ex = Assertions.assertThrows(InvalidIdException.class, () -> categoryController.addCategory(1222, new Category(name), token));
        Assertions.assertEquals(ex.getMessage(), "the father Category doesnt exist");
        /** Exception Tests **/

        categoryController.addCategory(0, new Category(name), token);
        Assertions.assertNotEquals(categoryRepository.getByName(name), null);

    }

    @Test
    void removeACategory() throws InvalidIdException, InvalidTokenException, NoAccessException, NoObjectIdException, InvalidFormatException, PasswordIsWrongException, InvalidAuthenticationException, NotLoggedINException, ObjectAlreadyExistException {
        /** Exception Tests **/
        authenticationController.login("test5", "test5", token);
        Exception ex = Assertions.assertThrows(NoAccessException.class, () -> categoryController.removeACategory(0, 2, token));
        Assertions.assertEquals(ex.getMessage(), "only manager can remove the Category.");
        authenticationController.logout(token);
        /** Exception Tests **/

        String name = createRandomName();
        authenticationController.login("aria", "aria", token);
        categoryController.addCategory(0, new Category(name), token);
        categoryController.removeACategory(categoryRepository.getByName(name).getId(), 0, token);
        Assertions.assertEquals(null, categoryRepository.getByName(name));

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
    public void addProductTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException, NoAccessException, InvalidIdException {

        /** Exception Tests **/
        authenticationController.login("test1", "test1", token);
        Exception ex = Assertions.assertThrows(NoAccessException.class, () -> categoryController.addProduct(2, 3, token));
        Assertions.assertEquals(ex.getMessage(), "only manager can add product to category");
        authenticationController.logout(token);

        authenticationController.login("aria", "aria", token);
        ex = Assertions.assertThrows(InvalidIdException.class, () -> categoryController.addProduct(2, 22222, token));
        Assertions.assertEquals(ex.getMessage(), "no product exist with 22222 id");

        ex = Assertions.assertThrows(InvalidIdException.class, () -> categoryController.addProduct(12, 21, token));
        Assertions.assertEquals(ex.getMessage(), "product by 21 id already exist");
        /** Exception Tests **/

        categoryController.addProduct(13,21,token);

    }

    @Test
    void removeProductTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException {
        /** Exception Tests **/
        authenticationController.login("test1", "test1", token);
        Exception ex = Assertions.assertThrows(NoAccessException.class, () -> categoryController.removeProduct(2, 3, token));
        Assertions.assertEquals(ex.getMessage(), "only manager can remove product");
        authenticationController.logout(token);

        authenticationController.login("aria", "aria", token);
        ex = Assertions.assertThrows(InvalidIdException.class, () -> categoryController.removeProduct(2, 22222, token));
        Assertions.assertEquals(ex.getMessage(), "no product exist with 22222 id");

        ex = Assertions.assertThrows(InvalidIdException.class, () -> categoryController.removeProduct(12, 25, token));
        Assertions.assertEquals(ex.getMessage(), "there is no product in this category by 25 id");
        /** Exception Tests **/
    }


    @Test
    public void getAllProductWithFilter() throws InvalidIdException {
        /** Exception Tests **/
        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> categoryController.getAllProductWithFilter(null,"name",true,0,0,5000,token));
        Assertions.assertEquals(ex.getMessage(),"No such category exists");
        /**Exception Tests **/

        Assertions.assertNotEquals(null,categoryController.getAllProductWithFilter(new HashMap<>(),"name",true,12,token));

    }

    @Test
    public void getProductsTest() throws InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, PasswordIsWrongException, NotLoggedINException, NoAccessException, InvalidIdException {

        /** Exception Tests **/
        authenticationController.login("test1", "test1", token);
        Exception ex = Assertions.assertThrows(NoAccessException.class, () -> categoryController.getProducts(2, token));
        Assertions.assertEquals(ex.getMessage(), "you are not manager.");
        authenticationController.logout(token);
        /**Exception Tests **/

        authenticationController.login("aria", "aria", token);
        Assertions.assertNotEquals(null, categoryController.getProducts(7, token));
    }

    private String createRandomName() {
        String randomName = "randomCategory";
        randomName += LocalTime.now();
        return randomName;
    }
}
