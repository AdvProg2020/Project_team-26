package repository;

import model.ProductSeller;

public interface ProductSellerRepository extends Repository<ProductSeller> {

    public void addRequest(ProductSeller productSeller);
}
