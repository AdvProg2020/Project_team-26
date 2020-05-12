package model.mysql;

import model.Category;
import model.repository.CategoryRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Map;

public class MySQLCategoryRepository
        extends MySQLRepository implements CategoryRepository {
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
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
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
