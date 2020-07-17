package client.controller.interfaces.review;

import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotBoughtTheProductException;

public interface IRatingController {

    void rate(double rating, int productId, String token) throws NoAccessException, NotBoughtTheProductException, InvalidTokenException;
}
