package repository;

import model.ProductSeller;
import model.ProductSellerRequest;

import java.util.List;

public interface ProductSellerRepository extends Repository<ProductSeller> {

    void addRequest(ProductSeller productSeller);

    void editRequest(ProductSeller productSeller);

    void deleteRequest(int id);

    void acceptRequest(int requestId);

    void rejectRequest(int requestId);

    ProductSellerRequest getProductSellerRequestById(int requestId);

    List<ProductSellerRequest> getAllRequests(Pageable pageable); //todo

    ProductSeller getProductSellerByIdAndSellerId(int productId, int sellerId);
}
