package model.repository.fake;

import exception.NoObjectIdException;
import model.Product;
import model.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static model.State.CHECKING_FOR_ADD;

public class FakeProductRepository implements ProductRepository {

    List<Product> allProducts;

    public FakeProductRepository() {
        this.allProducts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            allProducts.add(new Product());
            Product obj = allProducts.get(i);
            obj.setBrand("brand" + i);
            obj.setAverageRate(i * 5.5 / 7.1);
            obj.setDescription("this is product:" + i);
            obj.setName("" + i);
            //obj.setState(CHECKING_FOR_ADD);
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
        //TODO
    }

    @Override
    public List<Product> getAll() {
        return allProducts;
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
    public List<Product> getAllBySortAndFilter(Map<String, String> filter, String sortField, boolean isAscending) {
        return null;
    }

    @Override
    public void save(Product object) {
        if (object != null)
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
