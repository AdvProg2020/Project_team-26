package repository;

import model.Rate;

public interface RatingRepository extends Repository<Rate> {
    public boolean doesItMatch(int rateId, int customerId);
    public void editRate(int RateId, double newRating);
}
