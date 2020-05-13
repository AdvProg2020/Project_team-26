package model.repository.mysql;

import model.Category;
import model.repository.CategoryRepository;

import javax.persistence.*;
import java.util.ArrayList;
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
        EntityManager em = entityManagerFactory.createEntityManager();
        String tableName = Category.class.getAnnotation(Table.class).name();
        String query = "SELECT t FROM tableName t";

        TypedQuery<Category> typedQuery = em.createQuery(query, Category.class);
        try {
            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    @Override
    public Category getById(int id) {
        EntityManager em = entityManagerFactory.createEntityManager();

        try {
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public void save(Category object) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            em.persist(object);
            et.commit();
        } catch (Exception e) {
            if (et != null) {
                et.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
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
