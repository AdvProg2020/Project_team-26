package Server.controller;

import Server.controller.interfaces.auction.IAuctionController;
import exception.*;
import model.Auction;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class AuctionController implements IAuctionController {
    @Override
    public List<Auction> getAllAuction() {
        return null;
    }

    @Override
    public List<Auction> getAuctionForCustomer() throws NotLoggedINException {
        return null;
    }

    @Override
    public Auction getAuctionForSeller(String token) throws NotLoggedINException {
        return null;
    }

    @Override
    public void createNewAuction(int productSellerId, Date endDate, String token) throws ObjectAlreadyExistException, NotLoggedINException, NotSellerException, InvalidTokenException {

    }

    @Override
    public void increasePrice(int AuctionId, long newPrice, String token) throws InvalidIdException, NotLoggedINException, InvalidTokenException {

    }

    @Override
    public void participateInAuction(int AuctionId, long price, String token) throws InvalidIdException, NotLoggedINException, InvalidTokenException, NotEnoughCreditException {

    }
}
