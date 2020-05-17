package controller.interfaces.review;

import exception.*;

public interface IRatingController {

    void addRating(double rating, int productId, String token) throws NoAccessException, NotBoughtTheProductException, InvalidTokenException;
}
