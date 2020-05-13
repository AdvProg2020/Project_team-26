package controller.interfaces.review;

import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotBoughtTheProductException;
import exception.NotLoggedINException;

public interface IRatingController {

    void addARating(double rating, int productId, String token) throws NoAccessException, NotBoughtTheProductException, InvalidTokenException;

    void removeRating(int id, String token) throws NoAccessException, InvalidTokenException, NotLoggedINException;

    void editRating(int id, double  newRating, String token) throws NoAccessException, InvalidTokenException;
}
