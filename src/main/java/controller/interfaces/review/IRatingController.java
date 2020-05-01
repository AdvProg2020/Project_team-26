package controller.interfaces.review;

import exception.NoAccessException;
import exception.NotBoughtTheProductException;

public interface IRatingController {

    void addARating(double rating, int productId, String token) throws NoAccessException, NotBoughtTheProductException;

    void removeRating(int id, String token) throws NoAccessException;

    void editRating(int id, double  newRating, String token) throws NoAccessException;
}
