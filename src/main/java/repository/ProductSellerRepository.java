package repository;

import model.ProductSeller;
import model.ProductSellerRequest;

import java.util.List;

public interface ProductSellerRepository extends Repository<ProductSeller> {

    public void addRequest(ProductSeller productSeller);

    void acceptRequest(int requestId);

    void rejectRequest(int requestId);

    ProductSellerRequest getProductSellerRequestById(int requestId);

    List<ProductSellerRequest> getAllRequests(String sortField, boolean isAscending);
}
