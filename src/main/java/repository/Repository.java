package repository;

import exception.NoObjectIdException;

import java.util.List;
import java.util.Map;

public interface Repository<T> {

    public List<T> getAll();
    public T getById(int id);
    public void save(T object);
    public void delete(int id) throws NoObjectIdException;
    public void delete(T object) throws NoObjectIdException;
    public boolean exist(int id);
    List<T> getAllBySortAndFilter(Map<String,String> filter,String sortField,boolean isAscending);
}
