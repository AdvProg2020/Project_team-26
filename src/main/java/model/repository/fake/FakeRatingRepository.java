package model.repository.fake;

import model.Comment;
import model.Customer;
import model.Rate;
import model.repository.RatingRepository;
import model.repository.RepositoryContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FakeRatingRepository implements RatingRepository {

    List<Rate> allRatings;
    public static int lastId = 5;
    private RepositoryContainer repositoryContainer;
    private FakeUserRepository fakeUserRepository;
    private FakeProductRepository fakeProductRepository;

    public FakeRatingRepository() {
        allRatings = new ArrayList<>();
        fakeUserRepository = new FakeUserRepository();
        fakeProductRepository = new FakeProductRepository();
        for (int i = 0; i < 4; i++) {
            save(new Rate((Customer)fakeUserRepository.getById(i + 8),5.0,fakeProductRepository.getById(i)));
        }
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
        lastId++;
        object.setId(lastId);
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

    @Override
    public List<Rate> getAllBySortAndFilter(Map<String, String> filter, String sortField, boolean isAscending) {
        return null;
    }
}
