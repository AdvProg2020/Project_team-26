package repository;

import model.Off;

import java.util.List;

public interface OffRepository extends Repository<Off> {
    void addRequest(Off off);

    void editRequest(Off off);

    void deleteRequest(int id);

    List<Off> getAllOffForSellerWithFilter(String sortField, boolean isAscending, int SellerId);
}
