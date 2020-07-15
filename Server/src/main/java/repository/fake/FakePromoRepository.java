package repository.fake;

import exception.NoObjectIdException;
import model.Customer;
import model.Promo;
import repository.Pageable;
import repository.PromoRepository;
import repository.RepositoryContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FakePromoRepository implements PromoRepository {

    private int lastId = 5;
    private List<Promo> allPromos;
    private FakeUserRepository userRepository;
    private RepositoryContainer repositoryContainer;

    public FakePromoRepository() {
        userRepository = new FakeUserRepository();
        allPromos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            save(new Promo("Promo" + i, (Customer) userRepository.getUserByUsername("test8")));
        }
        Promo promo = new Promo("Promo" + 788, (Customer) userRepository.getUserByUsername("test8"));
        save(promo);
        promo.setId(7878);
        save(promo);
    }

    @Override
    public Promo getByCode(String promoCode) {
        List<Promo> promos = allPromos.stream().filter(promo -> promo.getPromoCode().equals(promoCode)).collect(Collectors.toList());
        if (promos.size() == 0)
            return null;
        return promos.get(0);
    }

    @Override
    public List<Promo> getAll() {
        return allPromos;
    }

    @Override
    public List<Promo> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public Promo getById(int id) {
        List<Promo> promos = allPromos.stream().filter(promo -> promo.getId() == id).collect(Collectors.toList());
        if (promos.size() == 0)
            return null;
        return promos.get(0);
    }

    @Override
    public void save(Promo object) {
        object.setMaxValidUse(10);
        if (object.getId() == 0) {
            lastId++;
            object.setId(lastId);
        }
        allPromos.remove(object);
        allPromos.add(object);
    }

    @Override
    public void delete(int id) throws NoObjectIdException {
        for (Promo promo : allPromos) {
            if (promo.getId() == id) {
                allPromos.remove(promo);
                return;
            }
        }
    }

    @Override
    public void delete(Promo object) throws NoObjectIdException {
        allPromos.remove(object);
    }

    @Override
    public boolean exist(int id) {
        return false;
    }

    @Override
    public List<Promo> getAllSorted(String sortField, boolean isAscending) {
        return null;
    }
}
