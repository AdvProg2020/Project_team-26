package client.exception;

import client.model.ProductSeller;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class NotEnoughProductsException extends Exception {

    ProductSeller productSeller;

    public NotEnoughProductsException(String message, ProductSeller productSeller) {
        super(message);
        this.productSeller = productSeller;
    }

    public ProductSeller getProductSeller() {
        return productSeller;
    }

    public static NotEnoughProductsException getHttpException(String errorMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(errorMessage, NotEnoughProductsException.class);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return new NotEnoughProductsException("not enough product",null);
        }
    }
}
