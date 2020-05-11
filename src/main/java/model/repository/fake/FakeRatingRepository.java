package model.repository.fake;

import model.Rate;
import model.repository.RatingRepository;

import java.util.ArrayList;
import java.util.List;

public class FakeRatingRepository implements RatingRepository {

    List<Rate> allRatings;

    public FakeRatingRepository() {
        allRatings = new ArrayList<>();
    }

    @Override
    public boolean doesItMatch(int rateId, int customerId) {
        if(getById(rateId).getCustomerId() == customerId)) {
            return true;
        }
        return false;
    }

    @Override
    public void editRate(int RateId, double newRating) {
        getById(RateId).setRate(newRating);
    }

    @Override
    public List<Rate> getAll() {
        return null;
    }

    @Override
    public Rate getById(int id) {
        return null;
    }

    @Override
    public void save(Rate object) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(Rate object) {

    }

    @Override
    public boolean exist(int id) {
        return false;
    }
}
