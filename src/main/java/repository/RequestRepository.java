package repository;

import model.Request;

public interface RequestRepository extends Repository<Request> {

    void accept(int id);
    void reject(int id);
}
