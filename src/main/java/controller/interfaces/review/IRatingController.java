package controller.interfaces.review;

import exception.*;

public interface IRatingController {

    void rate(double rating, int productId, String token) throws NoAccessException, NotBoughtTheProductException, InvalidTokenException;
}
