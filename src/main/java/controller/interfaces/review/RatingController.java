package controller.interfaces.review;

public interface RatingController {

    void addARating(double rating, int productId, String token);

    void removeRating(int id, String token);

    void editRating(int id, double  newRating, String token);
}
