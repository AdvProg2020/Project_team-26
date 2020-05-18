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

    void acceptRequest(int requestId);

    void rejectRequest(int requestId);

    public List<Product> getAllProductInOff(Map<String, String> filter, String sortFiled, boolean isAscending);

    OffRequest getOffRequestById(int requestId);

    List<OffRequest> getAllRequests(String sortField, boolean isAscending);

    List<Off> getAllOfForSellerWithFilter(String sortField, boolean isAscending, int SellerId);
}
