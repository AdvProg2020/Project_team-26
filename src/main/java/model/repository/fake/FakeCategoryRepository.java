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
         //   allCategory.add(new Category(i + 1));
            Category category = allCategory.get(i);
           // category.setName("" + i);//todo
            category.setParent(getByName("" + i % 3));
        }


    }

    @Override
    public Category getByName(String name) {
        return null;
    }

    @Override
    public void edit(Category category, String Filed, String replacement) {

    }

    @Override
    public List<Category> getAll() {
        return null;
    }

    @Override
    public Category getById(int id) {
        return null;
    }

    @Override
    public void save(Category object) {

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
}
