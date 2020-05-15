package repository.fake;

import model.ProductSeller;
import repository.ProductRepository;
import repository.ProductSellerRepository;
import repository.RepositoryContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FakeProductSellerRepository implements ProductSellerRepository {
    List<ProductSeller> allProductSellers ;
    public static int lastId = 5;
    private FakeProductRepository productRepository;

    public FakeProductSellerRepository() {
        allProductSellers = new ArrayList<>();
        productRepository = new FakeProductRepository();
        for(int i = 0; i < 5; i++) {
            allProductSellers.add(new ProductSeller());
        }
    }

    @Override
    public void addRequest(ProductSeller productSeller) {

    }

    @Override
    public List<ProductSeller> getAll() {
        return allProductSellers;
    }

    @Override
    public ProductSeller getById(int id) {
        List<ProductSeller> productSellers = allProductSellers.stream().filter(ProductSeller -> ProductSeller.getId() == id).collect(Collectors.toList());
        if (productSellers.size() == 0)
            return null;
        return productSellers.get(0);
    }

    @Override
    public void save(ProductSeller object) {
        allProductSellers.add(object);
    }

    @Override
    public void delete(int id) {
        allProductSellers.remove(productRepository.getById(id));
    }

    @Override
    public void delete(ProductSeller object) {
        allProductSellers.remove(object);
    }

    @Override
    public boolean exist(int id) {
        for (ProductSeller productSeller : allProductSellers) {
            if (productSeller.getId() == id)
                return true;
        }
        return false;
    }

    @Override
    public List<ProductSeller> getAllSorted(String sortField, boolean isAscending) {
        return null;
    }

}
