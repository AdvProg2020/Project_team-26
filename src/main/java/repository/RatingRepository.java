package repository;

import model.Rate;

public interface RatingRepository extends Repository<Rate> {
    boolean doesItMatch(int rateId, int customerId);

    void editRate(int RateId, double newRating);
}
