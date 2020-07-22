package repository.mysql;

import exception.NoObjectIdException;
import model.Auction;
import repository.AuctionRepository;
import repository.Pageable;

import java.util.List;

public class MySQLAuctionRepository extends MySQLRepository<Auction> implements AuctionRepository {
    public MySQLAuctionRepository() {
        super(Auction.class);
    }

    @Override
    public List<Auction> getAll() {
        return null;
    }

    @Override
    public List<Auction> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public Auction getById(int id) {
        return null;
    }

    @Override
    public void save(Auction object) {

    }

    @Override
    public boolean exist(int id) {
        return false;
    }

    @Override
    public List<Auction> getAllSorted(String sortField, boolean isAscending) {
        return null;
    }

    @Override
    public boolean doesProductSellerExist(int productSellerId) {
        return false;
    }
}
