package model.repository.mysql;

import model.Product;
import model.repository.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Map;

public class MySQLProductRepository
        extends MySQLRepository implements ProductRepository {

    @Override
    public Product getByName(String name) {
        return null;
    }

    @Override
    public void addRequest(Product product) {

    }

    @Override
    public void editRequest(Product product) {

    }

    @Override
    public void deleteRequest(int id) {

    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public Product getById(int id) {
        return null;
    }

    @Override
    public void save(Product object) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(Product object) {

    }

    @Override
    public boolean exist(int id) {
        return false;
    }

    @Override
    public List<Product> getAllBySortAndFilter(Map<String, String> filter, String sortField, boolean isAscending) {
        return null;
    }
}
