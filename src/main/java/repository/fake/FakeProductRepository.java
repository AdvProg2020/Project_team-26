package repository.fake;

import exception.NoObjectIdException;
import model.Product;
import model.ProductRequest;
import model.ProductSeller;
import model.User;
import repository.Pageable;
import repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FakeProductRepository implements ProductRepository {

    private List<Product> allProducts;
    private int lastId = 0;

    public FakeProductRepository() {
        this.allProducts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            allProducts.add(new Product());
            Product obj = allProducts.get(i);
            obj.setBrand("brand" + i);
            obj.setAverageRate(i * 5.5 / 7.1);
            obj.setDescription("this is product:" + i);
            obj.setName("" + i);
            save(obj);
        }
    }


    @Override
    public Product getByName(String name) {
        for (Product product : allProducts) {
            if (product.getName().equals(name))
                return product;
        }
        return null;
    }

    @Override
    public void addRequest(Product product, User requestedBy) {

    }

    @Override
    public void editRequest(Product product, User requestedBy) {

    }

    @Override
    public void deleteRequest(int id, User requestedBy) {

    }
    @Override
    public List<Product> getAll() {
        return allProducts;
    }

    @Override
    public List<Product> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public Product getById(int id) {
        for (Product product : allProducts) {
            if (product.getId() == id)
                return product;
        }
        return null;
    }

    @Override
    public boolean exist(int id) {
        for (Product product : allProducts) {
            if (product.getId() == id)
                return true;
        }
        return false;
    }

    @Override
    public List<Product> getAllSorted(String sortField, boolean isAscending) {
        return null;
    }

    @Override
    public List<Product> getAllSortedAndFiltered(Map<String, String> filter, Pageable pageable) {
        return null;
    }

    @Override
    public List<Product> getAllSortedAndFilteredInOff(Map<String, String> filter, Pageable pageable) {
        return null;
    }

    @Override
    public List<Product> getAllProductsWithFilterForSeller(Map<String, String> filter, Pageable pageable, int sellerId) {
        return null;
    }
    @Override
    public void save(Product object) {
        if (object == null)
            return;

        if(object.getId() == 0) {
            lastId++;
            object.setId(lastId);
        }
        allProducts.add(object);
    }


    @Override
    public void delete(int id) throws NoObjectIdException {
        Product product = getById(id);
        delete(product);
    }

    @Override
    public void delete(Product object) throws NoObjectIdException {
        if (object != null) {
            allProducts.remove(object);
        } else {
            throw new NoObjectIdException("Specified object does not exist.");
        }
    }
}
