package model.repository.fake;

import model.Product;
import model.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class FakeProductRepository implements ProductRepository {

    List<Product> allProducts;

    public FakeProductRepository() {
        this.allProducts = new ArrayList<>();
    }

    @Override
    public List<Product> getAll() {
        return allProducts;
    }

    @Override
    public Product getById(int id) {
        return allProducts.stream().filter(product -> product.getId() == id).findAny().get();
    }

    @Override
    public void save(Product object) {
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(Product object) {

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
}
