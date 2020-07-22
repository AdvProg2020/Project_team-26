package repository;

import model.Auction;

public interface AuctionRepository extends Repository<Auction> {

    public boolean doesProductSellerExist(int productSellerId);
}
