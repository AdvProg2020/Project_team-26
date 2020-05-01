package model.repository;

import java.util.List;

public interface Repository<T> {

    public List<T> getAll();
    public T getById(int id);
    public void save(T object);
    public void delete(int id);
    public void delete(T object);
}
