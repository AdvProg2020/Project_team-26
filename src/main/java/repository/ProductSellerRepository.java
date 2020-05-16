package repository;

import model.ProductSeller;
import model.ProductSellerRequest;

import java.util.List;

public interface ProductSellerRepository extends Repository<ProductSeller> {

    public void addRequest(ProductSeller productSeller);

    List<ProductSellerRequest> getAllRequests(String sortField, boolean isAscending);
}
