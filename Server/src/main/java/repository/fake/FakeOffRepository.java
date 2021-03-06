package repository.fake;

import exception.NoObjectIdException;
import model.Off;
import repository.OffRepository;
import repository.Pageable;

import java.util.ArrayList;
import java.util.List;

public class FakeOffRepository implements OffRepository {

    private List<Off> allOffs;
    private FakeUserRepository fakeUserRepository;
    private int lastId = 0;


    public FakeOffRepository() {
        fakeUserRepository = new FakeUserRepository();
        allOffs = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            save(new Off());
        }

    }

    @Override
    public void addRequest(Off off) {
        allOffs.add(off);
    }

    @Override
    public void editRequest(Off off) {

    }

    @Override
    public void deleteRequest(int id) {

    }

    @Override
    public List<Off> getAllOffForSellerWithFilter(String sortField, boolean isAscending, int SellerId) {
        return null;
    }

    @Override
    public List<Off> getAll() {
        return allOffs;
    }

    @Override
    public List<Off> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public Off getById(int id) {
        for (Off off : allOffs) {
            if(off.getId() == id) {
                return off;
            }
        }
        return null;
    }

    @Override
    public void save(Off object) {
        lastId++;
        object.setId(lastId);
        allOffs.add(object);
    }

    @Override
    public void delete(int id) throws NoObjectIdException {
        for (Off off : allOffs) {
            if(off.getId() == id) {
                allOffs.remove(off);
                return;
            }
        }
        throw new NoObjectIdException("No object Exists");
    }

    @Override
    public void delete(Off object) throws NoObjectIdException {
        if(allOffs.contains(object)) {
            allOffs.remove(object);
        } else {
            throw new NoObjectIdException("Object does not exist");
        }
    }

    @Override
    public boolean exist(int id) {
        for (Off off : allOffs) {
            if(off.getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Off> getAllSorted(String sortField, boolean isAscending) {
        return null;
    }
}
