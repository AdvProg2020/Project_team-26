package model.repository;

import model.Product;

public interface ProductRepository extends Repository<Product> {

    public Product getByName(String name);
    public void addRequest(Product product);
    public void editRequest(Product product);
    public void deleteRequest(int id);
   /* void editName(Product product , String replacement);
    void editState(Product product , State state);
    void editBrand(Product product , String name);
    void addSeller(Product product, ProductSeller productSeller);
    void removeSeller(Product product , ProductSeller productSeller);
    ProductSeller getAllSeller(Product product);
    void editCategory(Product product , )*/
}

