package model.repository.fake;

import model.Category;
import model.Product;
import model.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

import static model.State.CHECKING_FOR_ADD;

public class FakeCategoryRepository implements CategoryRepository {
    List<Category> allCategory;
    public static int lastId = 5;

    public FakeCategoryRepository() {
        this.allCategory = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            save(new Category("" + i + 1));
            Category category = allCategory.get(i);
            category.setParent(getByName("" + i % 3 + 1));
        }
    }

    @Override
    public Category getByName(String name) {
        return allCategory.stream().filter(category -> category.getName().equals(name)).findAny().get();
    }

    @Override
    public void edit(Category category, String Filed, String replacement) {


    }

    @Override
    public List<Category> getAll() {
        return allCategory;
    }

    @Override
    public Category getById(int id) {
        allCategory.stream().filter(category -> category.getId() == id).findAny().get();
        return null;
    }

    @Override
    public void save(Category object) {
        lastId++;
        object.setId(lastId);
        allCategory.add(object);
    }

    @Override
    public void delete(int id) {
        allCategory.remove(getById(id));
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
}
