package repository.mysql;

import model.Category;
import repository.CategoryRepository;

import java.util.List;
import java.util.Map;

public class MySQLCategoryRepository
        extends MySQLRepository<Category> implements CategoryRepository {

    public MySQLCategoryRepository() {
        super(Category.class);
    }

    @Override
    public Category getByName(String name) {
        return null;
    }

    @Override
    public void edit(Category category, String Filed, String replacement) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(Category object) {

    }

    @Override
    public boolean exist(int id) {
        return false;
    }

    @Override
    public List<Category> getAllBySortAndFilter(Map<String, String> filter, String sortField, boolean isAscending) {
        return null;
    }
}
