package repository;

import model.Off;
import model.OffItem;
import model.OffRequest;
import model.Product;

import java.util.List;
import java.util.Map;

public interface OffRepository extends Repository<Off> {
    void addRequest(Off off);

    void editRequest(Off off);

    void deleteRequest(int id);

    List<Off> getAllOffForSellerWithFilter(String sortField, boolean isAscending, int SellerId);
}
