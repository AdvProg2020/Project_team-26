package repository.fake;

import model.ProductSeller;
import model.ProductSellerRequest;
import model.Seller;
import repository.ProductRepository;
import repository.ProductSellerRepository;
import repository.RepositoryContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FakeProductSellerRepository implements ProductSellerRepository {
    List<ProductSeller> allProductSellers ;
    public int lastId = 5;
    private FakeProductRepository productRepository;
    private FakeUserRepository userRepository;

    public FakeProductSellerRepository() {
        allProductSellers = new ArrayList<>();
        productRepository = new FakeProductRepository();
        userRepository = new FakeUserRepository();
        for(int i = 0; i < 5; i++) {
            ProductSeller productSeller = new ProductSeller(lastId++,productRepository.getById(1),i);
            productSeller.setPrice(12);
            productSeller.setSeller((Seller) userRepository.getUserByUsername("test5"));
            save(productSeller);
        }
    }

    @Override
    public void addRequest(ProductSeller productSeller) {

    }

    @Override
    public void editRequest(ProductSeller productSeller) {

    }

    @Override
    public void deleteRequest(ProductSeller productSeller) {

    }

    @Override
    public void acceptRequest(int requestId) {

    }

    @Override
    public void rejectRequest(int requestId) {

    }

    @Override
    public ProductSellerRequest getProductSellerRequestById(int requestId) {
        return null;
    }

    @Override
    public List<ProductSellerRequest> getAllRequests(String sortField, boolean isAscending) {
        return null;
    }

    @Override
    public ProductSeller getProductSellerByIdAndSellerId(int productId, int sellerId) {
        return null;
    }

    @Override
    public List<ProductSeller> getAll() {
        return allProductSellers;
    }

    @Override
    public ProductSeller getById(int id) {
        for (ProductSeller productSeller : allProductSellers) {
            if(productSeller.getId() == id) {
                return productSeller;
            }
        }
        return null;
    }

    @Override
    public void save(ProductSeller object) {
        lastId++;
        object.setId(lastId);
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
