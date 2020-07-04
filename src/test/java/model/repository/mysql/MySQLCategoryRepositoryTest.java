package model.repository.mysql;

import model.Category;
import model.Product;
import repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ProductRepository;
import repository.RepositoryContainer;
import repository.mysql.MySQLCategoryRepository;
import repository.mysql.MySQLProductRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

class MySQLCategoryRepositoryTest {

    CategoryRepository categoryRepository;
    ProductRepository productRepository;

    @BeforeEach
    void setup() {
        RepositoryContainer container = new RepositoryContainer("sql");
        categoryRepository = (CategoryRepository) container.getRepository("CategoryRepository");
        productRepository = (ProductRepository) container.getRepository("ProductRepository");
    }

    @Test
    void getAll() {
        List<Category> categories = categoryRepository.getAll();
        Assertions.assertEquals(4, categories.size());

        Category category = categories.get(1);
        Assertions.assertEquals(2, category.getId());
        Assertions.assertEquals("Category 1", category.getName());
        Assertions.assertEquals(1, category.getParent().getId());

        Product product = category.getProducts().get(0);
        Assertions.assertEquals(1, product.getId());
        Assertions.assertEquals("Product 1", product.getName());
        Assertions.assertEquals("Brand 1", product.getBrand());
        Assertions.assertEquals("Great product from brand 1", product.getDescription());

        product = category.getProducts().get(1);
        Assertions.assertEquals(2, product.getId());
        Assertions.assertEquals("Product 2", product.getName());
        Assertions.assertEquals("Brand 1", product.getBrand());
        Assertions.assertEquals("Great product from brand 1", product.getDescription());


        category = categories.get(2);
        Assertions.assertEquals(3, category.getId());
        Assertions.assertEquals("Category 2", category.getName());
        Assertions.assertEquals(1, category.getParent().getId());

        product = category.getProducts().get(0);
        Assertions.assertEquals(3, product.getId());
        Assertions.assertEquals("Product 3", product.getName());
        Assertions.assertEquals("Brand 1", product.getBrand());
        Assertions.assertEquals("Another great product from brand 1", product.getDescription());
    }

    @Test
    void getById() {
        Category category = categoryRepository.getById(2);
        Assertions.assertEquals(2, category.getId());
        Assertions.assertEquals("Category 1", category.getName());
        Assertions.assertEquals(1, category.getParent().getId());

        Product product = category.getProducts().get(0);
        Assertions.assertEquals(1, product.getId());
        Assertions.assertEquals("Product 1", product.getName());
        Assertions.assertEquals("Brand 1", product.getBrand());
        Assertions.assertEquals("Great product from brand 1", product.getDescription());

        product = category.getProducts().get(1);
        Assertions.assertEquals(2, product.getId());
        Assertions.assertEquals("Product 2", product.getName());
        Assertions.assertEquals("Brand 1", product.getBrand());
        Assertions.assertEquals("Great product from brand 1", product.getDescription());


        category = categoryRepository.getById(3);
        Assertions.assertEquals(3, category.getId());
        Assertions.assertEquals("Category 2", category.getName());
        Assertions.assertEquals(1, category.getParent().getId());

        product = category.getProducts().get(0);
        Assertions.assertEquals(3, product.getId());
        Assertions.assertEquals("Product 3", product.getName());
        Assertions.assertEquals("Brand 1", product.getBrand());
        Assertions.assertEquals("Another great product from brand 1", product.getDescription());

        category = categoryRepository.getById(100);
        Assertions.assertNull(category);
    }

    @Test
    void save() {
        Category category = categoryRepository.getById(4);
        String name = category.getName();
        category.setName(name + "a");
        categoryRepository.save(category);

        category = categoryRepository.getById(4);
        Assertions.assertEquals(name + "a", category.getName());
        int parentId = category.getParent().getId();
        category.setParent(categoryRepository.getById((parentId % 3) + 1));
        categoryRepository.save(category);

        categoryRepository.getById(4);
        Assertions.assertEquals((parentId % 3) + 1, category.getParent().getId());


        List<Category> categories = categoryRepository.getAll();
        category = categories.get(3);
        name = category.getName();
        category.setName(name + "b");
        categoryRepository.save(category);

        categories = categoryRepository.getAll();
        category = categories.get(3);
        Assertions.assertEquals(name + "b", category.getName());
        parentId = category.getParent().getId();
        category.setParent(categoryRepository.getById((parentId % 3) + 1));
        Product product = new Product("Product " + category.getName(), "Brand 1", "Great product");
        product.setCategory(category);
        category.getProducts().add(product);
        categoryRepository.save(category);

        categories = categoryRepository.getAll();
        category = categories.get(3);
        Assertions.assertEquals((parentId % 3) + 1, category.getParent().getId());
    }

    @Test
    public void editPhoto() throws IOException {
        File file = new File("C:/Users/pouya/Desktop/photo_2020-07-03_17-06-03.jpg");
        Product product = productRepository.getById(1);
        product.setImage(Files.readAllBytes(file.toPath()));
        productRepository.save(product);
    }
}