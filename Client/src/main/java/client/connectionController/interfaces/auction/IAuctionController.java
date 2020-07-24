package client.connectionController.interfaces.auction;


import client.exception.*;
import client.model.Auction;

import java.util.Date;
import java.util.List;

public interface IAuctionController {
    List<Auction> getAllAuction(String token);

    void createNewAuction(int productSellerId, Date endDate, String token) throws ObjectAlreadyExistException, NotLoggedINException, NotSellerException, InvalidTokenException;

    void participateInAuction(int auctionId, Long price, String token) throws InvalidIdException, NotLoggedINException, InvalidTokenException, NotCustomerException, NoAccessException;

}
