package repository.fake;

import model.Category;
import model.Product;
import repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FakeCategoryRepository implements CategoryRepository {
    List<Category> allCategory;
    public int lastId = 1;

    public FakeCategoryRepository() {
        this.allCategory = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            save(new Category("" + i + 1));
            Category category = allCategory.get(i);
            category.setParent(getByName("" + i % 3 + 1));
        }
    }

    @Override
    public Category getByName(String name) {
        List<Category> categories = allCategory.stream().filter(category -> category.getName().equals(name)).collect(Collectors.toList());
        if (categories.size() == 0)
            return null;
        return categories.get(0);
    }

    @Override
    public List<Product> getAllProductWithFilter(Map<String, String> filter, String sortField, boolean isAscending) {
        return null;
    }

    @Override
    public List<Category> getAll() {
        return allCategory;
    }

    @Override
    public Category getById(int id) {
        List<Category> categories = allCategory.stream().filter(category -> category.getId() == id).collect(Collectors.toList());
        if (categories.size() == 0)
            return null;
        return categories.get(0);
    }

    @Override
    public void save(Category object) {
        if (object.getId() == 0) {
            lastId++;
            object.setId(lastId);
            allCategory.add(object);
        }
        allCategory.remove(getById(object.getId()));
        allCategory.add(object);
    }

    @Override
    public void delete(int id) {
        this.allCategory.remove(getById(id));
    }

    @Override
    public void delete(Category object) {
        if (allCategory.contains(object))
            allCategory.remove(object);
    }

    @Override
    public boolean exist(int id) {
        return false;
    }

    @Override
    public List<Category> getAllSorted(String sortField, boolean isAscending) {
        return null;
    }

}
