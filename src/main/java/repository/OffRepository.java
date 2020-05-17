package repository;

import model.Off;
import model.OffItem;
import model.OffRequest;

import java.util.List;

public interface OffRepository extends Repository<Off> {
    void addRequest(Off off);

    void editRequest(Off off);

    void deleteRequest(int id);

    void acceptRequest(int requestId);

    void rejectRequest(int requestId);

    OffRequest getOffRequestById(int requestId);

    List<OffRequest> getAllRequests(String sortField, boolean isAscending);
}
