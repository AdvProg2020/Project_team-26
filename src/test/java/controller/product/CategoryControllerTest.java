package controller.product;

import controller.category.CategoryController;
import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.ObjectAlreadyExistException;
import model.Category;
import model.Product;
import model.Session;
import model.repository.CategoryRepository;
import model.repository.RepositoryContainer;
import model.repository.UserRepository;
import org.junit.Assert;
import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CategoryControllerTest {

    RepositoryContainer repositoryContainer;
    CategoryController categoryController;
    CategoryRepository categoryRepository;
    String token;

    @Before
    void setup(){
        repositoryContainer = new RepositoryContainer();
        Session.initializeFake((UserRepository) repositoryContainer.getRepository("UserRepository"));
        categoryController = new CategoryController(repositoryContainer);
        categoryRepository = (CategoryRepository) repositoryContainer.getRepository("CategoryRepository");
        token = Session.addSession();
    }

    @Test
    void addCategory() {
        try {
            categoryController.addCategory(0, "new", token);
        } catch (InvalidIdException e) {
            e.printStackTrace();
        } catch (ObjectAlreadyExistException e) {
            e.printStackTrace();
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }
    }

    @Test
    void removeACategory() throws InvalidIdException, InvalidTokenException, NoAccessException {
        List<Category> categories = new ArrayList<>();

        categoryController.removeACategory(5, 0, token);
    }

    @Test
    void getAllCategoriesWith() throws InvalidIdException {
        List<Category> categoryControllerList = categoryController.getAllCategories(0, token);
        Assert.assertEquals(categoryControllerList, repositoryContainer.getRepository("CategoryRepository").getAll());
        /** check exception*/
       Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> categoryController.getAllCategories(98,token));
       Assert.assertEquals(ex.getMessage(),"no category exist.");
        /** end exception test*/
    }

    @Test
    void getCategory() throws InvalidIdException {
        Category category1 = categoryRepository.getById(6);
        Category category = categoryController.getCategory(6, token);
        Assert.assertEquals(category1, category);
        /** check exception*/
        Exception ex = Assertions.assertThrows(InvalidIdException.class, () -> categoryController.getAllCategories(98,token));
        Assert.assertEquals(ex.getMessage(),"no category exist.");
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
            // Assert.assertEquals(productList.);
        } catch (InvalidIdException e) {
            e.printStackTrace();
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }
    }
}
