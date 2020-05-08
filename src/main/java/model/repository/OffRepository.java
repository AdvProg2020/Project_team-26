package model.repository;

import model.Off;
import model.OffItem;
import model.Product;

public interface OffRepository extends Repository<Off> {
    public Off getOffByStringCode(String stringCode);

    OffItem getItemByProductIdFromAllOffa(int id);

    void addRequest(Off off);
    void editRequest(Off off);
    void deleteRequest(int id);
}
