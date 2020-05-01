package model.repository.fake;

import model.Category;
import model.Product;
import model.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

import static model.State.CHECKING_FOR_ADD;

public class FakeCategoryRepository implements CategoryRepository {
    List<Category> allCategory;

    public FakeCategoryRepository() {
        this.allCategory = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            allCategory.add(new Category(i + 1));
            Category category = allCategory.get(i);
            category.setName("" + i);
            category.setParent(getByName("" + i % 3));
        }


    }


    @Override
    public Category getByName(String name) {
        for (Category category : allCategory) {
            if (category.getName().equals(name))
                return category;
        }

        return null;
    }

    @Override
    public void edit(Category category, String Filed, String replacement) {

    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public Object getById(int id) {
        return null;
    }

    @Override
    public boolean exist(int id) {
        return false;
    }

    @Override
    public void save(Object object) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(Object object) {

    }
}
