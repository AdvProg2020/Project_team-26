package exception;

import model.ProductSeller;

public class NotEnoughProductsException extends Exception {

    ProductSeller productSeller;

    public NotEnoughProductsException(String message, ProductSeller productSeller) {
        super(message);
        this.productSeller = productSeller;
    }

    public ProductSeller getProductSeller() {
        return productSeller;
    }
}
