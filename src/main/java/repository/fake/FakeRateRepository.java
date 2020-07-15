package repository.fake;

import model.Customer;
import model.Rate;
import repository.Pageable;
import repository.RateRepository;
import repository.RepositoryContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FakeRateRepository implements RateRepository {

    List<Rate> allRatings;
    public int lastId = 5;
    private RepositoryContainer repositoryContainer;
    private FakeUserRepository fakeUserRepository;
    private FakeProductRepository fakeProductRepository;

    public FakeRateRepository() {
        allRatings = new ArrayList<>();
        fakeUserRepository = new FakeUserRepository();
        fakeProductRepository = new FakeProductRepository();
        for (int i = 0; i < 4; i++) {
            save(new Rate((Customer)fakeUserRepository.getById(i + 8),5.0,fakeProductRepository.getById(i)));
        }
    }

    @Override
    public List<Rate> getAll() {
        return allRatings;
    }

    @Override
    public List<Rate> getAll(Pageable pageable) {
        return null;
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
    public List<Rate> getAllSorted(String sortField, boolean isAscending) {
        return null;
    }


    @Override
    public Rate getCustomerRate(int customerId, int productId) {
        return null;
    }
}
