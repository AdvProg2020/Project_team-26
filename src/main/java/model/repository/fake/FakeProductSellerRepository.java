package model.repository.fake;

import model.Comment;
import model.Product;
import model.ProductSeller;
import model.repository.ProductRepository;
import model.repository.ProductSellerRepository;
import model.repository.RepositoryContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FakeProductSellerRepository implements ProductSellerRepository {
    List<ProductSeller> allProductSellers ;
    public static int lastId = 5;
    private ProductRepository productRepository;
    private RepositoryContainer repositoryContainer;

    public FakeProductSellerRepository() {
        repositoryContainer = new RepositoryContainer();
        allProductSellers = new ArrayList<>();
        productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
        for(int i = 0; i < 5; i++) {
            allProductSellers.add(new ProductSeller())
        }
    }

    @Override
    public void addRequest(ProductSeller productSeller) {

    }

    @Override
    public List<ProductSeller> getAll() {
        return null;
    }

    @Override
    public ProductSeller getById(int id) {
        return null;
    }

    @Override
    public void save(ProductSeller object) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(ProductSeller object) {

    }

    @Override
    public boolean exist(int id) {
        return false;
    }

    @Override
    public List<ProductSeller> getAllBySortAndFilter(Map<String, String> filter, String sortField, boolean isAscending) {
        return null;
    }
}
