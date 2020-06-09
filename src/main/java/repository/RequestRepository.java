package repository;

import model.Request;

import java.util.List;

public interface RequestRepository extends Repository<Request> {

    void accept(int id);

    void reject(int id);

    List<Request> getAllPending(int from, int count);
}
