package model.repository.mysql;

import model.Category;
import model.repository.CategoryRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MySQLCategoryRepositoryTest {

    CategoryRepository categoryRepository;

    @BeforeEach
    void setup() {
        categoryRepository = new MySQLCategoryRepository();
    }

    @Test
    void getById() {
        Category category = categoryRepository.getById(2);
        Assertions.assertEquals(2, category.getId());
        Assertions.assertEquals("Category 1", category.getName());
        Assertions.assertEquals(1, category.getParent().getId());


        category = categoryRepository.getById(100);
        Assertions.assertNull(category);
    }

    @Test
    void save() {
    }
}