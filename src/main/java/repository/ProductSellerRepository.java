package repository;

import model.ProductSeller;
import model.ProductSellerRequest;

import java.util.List;

public interface ProductSellerRepository extends Repository<ProductSeller> {

    void addRequest(ProductSeller productSeller);

    void editRequest(ProductSeller productSeller);

    void deleteRequest(int id);

    ProductSeller getProductSellerByIdAndSellerId(int productId, int sellerId);
}
