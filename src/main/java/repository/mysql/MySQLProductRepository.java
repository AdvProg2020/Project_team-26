package repository.mysql;

import model.Product;
import repository.ProductRepository;

import java.util.List;
import java.util.Map;

public class MySQLProductRepository
        extends MySQLRepository<Product> implements ProductRepository {

    public MySQLProductRepository() {
        super(Product.class);
    }

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
    public Object getAllRequests() {
        return null;
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
