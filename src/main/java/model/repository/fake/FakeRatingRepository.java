package model.repository.fake;

import model.Comment;
import model.Rate;
import model.repository.RatingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FakeRatingRepository implements RatingRepository {

    List<Rate> allRatings;

    public FakeRatingRepository() {
        allRatings = new ArrayList<>();
    }

    @Override
    public boolean doesItMatch(int rateId, int customerId) {
        if(getById(rateId).getCustomerId() == customerId) {
            return true;
        }
        return false;
    }

    @Override
    public void editRate(int RateId, double newRating) {
        getById(RateId).setScore(newRating);
    }

    @Override
    public List<Rate> getAll() {
        return allRatings;
    }

    @Override
    public Rate getById(int id) {
        List<Rate> ratings  = allRatings.stream().filter(Rate -> Rate.getId() == id).collect(Collectors.toList());
        if (ratings.size() == 0)
            return null;
        return ratings.get(0);
    }

    @Override
    public void save(Rate object) {
        allRatings.add(object);
    }

    @Override
    public void delete(int id) {
        allRatings.remove(getById(id));
    }

    @Override
    public void delete(Rate object) {
        allRatings.remove(object);
    }

    @Override
    public boolean exist(int id) {
        return false;
    }
}
