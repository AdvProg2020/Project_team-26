package interfaces.review;

import exception.*;

public interface IRatingController {

    void addARating(double rating, int productId, String token) throws NoAccessException, NotBoughtTheProductException, InvalidTokenException;

    void removeRating(int id, String token) throws NoAccessException, InvalidTokenException, NotLoggedINException, NoObjectIdException;

    void editRating(int id, double  newRating, String token) throws NoAccessException, InvalidTokenException, NotLoggedINException;
}
