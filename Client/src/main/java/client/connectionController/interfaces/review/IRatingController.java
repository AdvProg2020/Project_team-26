package client.connectionController.interfaces.review;


import client.exception.*;

public interface IRatingController {

    void rate(double rating, int productId, String token) throws NoAccessException, NotBoughtTheProductException, InvalidTokenException;
}
