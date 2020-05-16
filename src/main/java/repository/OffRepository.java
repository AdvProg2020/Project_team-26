package repository;

import model.Off;
import model.OffItem;
import model.OffRequest;

import java.util.List;

public interface OffRepository extends Repository<Off> {
    Off getOffByStringCode(String stringCode);

    OffItem getItemByProductIdFromAllOffs(int id);

    void addRequest(Off off);

    void editRequest(Off off);

    void deleteRequest(int id);

    List<OffRequest> getAllRequests(String sortField, boolean isAscending);
}
