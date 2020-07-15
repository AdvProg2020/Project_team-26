package repository;

import model.ProductSeller;

public interface ProductSellerRepository extends Repository<ProductSeller> {
    void addRequest(ProductSeller productSeller);

    void editRequest(ProductSeller productSeller);

    void deleteRequest(int id);

    ProductSeller getProductSellerByIdAndSellerId(int productId, int sellerId);
}
