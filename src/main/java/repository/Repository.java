package repository;

import exception.NoObjectIdException;

import java.util.List;
import java.util.Map;

public interface Repository<T> {

    List<T> getAll();

    List<T> getAll(Pageable pageable);

    T getById(int id);

    void save(T object);

    void delete(int id) throws NoObjectIdException;

    void delete(T object) throws NoObjectIdException;

    boolean exist(int id);

    List<T> getAllSorted(String sortField, boolean isAscending);
}
