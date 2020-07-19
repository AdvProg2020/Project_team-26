package client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.ProductSeller;
import org.springframework.web.client.UnknownHttpStatusCodeException;

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

    public static NotEnoughProductsException getHttpException(String errorMessage) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(errorMessage, NotEnoughProductsException.class);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }
}
