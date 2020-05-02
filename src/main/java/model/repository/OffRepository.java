package model.repository;

import model.Off;
import model.OffItem;

public interface OffRepository extends Repository<Off> {
    public Off getOffByStringCode(String stringCode);

    OffItem getItemByProductIdFromAllOffa(int id);
}
