package model.repository;

import model.Product;

public interface ProductRepository extends Repository<Product> {

    public Product getByName(String name);
    public void addRequest(Product product);
    public void editRequest(Product product);
    public void deleteRequest(int id);
}
