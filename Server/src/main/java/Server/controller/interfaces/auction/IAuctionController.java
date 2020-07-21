package Server.controller.interfaces.auction;

import exception.*;
import model.Auction;

import java.util.Date;
import java.util.List;

public interface IAuctionController {
    List<Auction> getAllAuction();

    List<Auction> getAuctionForCustomer() throws NotLoggedINException;

    Auction getAuctionForSeller(String token) throws NotLoggedINException;

    void createNewAuction(int productSellerId, Date endDate, String token) throws ObjectAlreadyExistException, NotLoggedINException, NotSellerException, InvalidTokenException;

    void increasePrice(int AuctionId, long newPrice, String token) throws InvalidIdException, NotLoggedINException, InvalidTokenException;

    void participateInAuction(int AuctionId, long price, String token) throws InvalidIdException, NotLoggedINException, InvalidTokenException, NotEnoughCreditException;


}
