package Server.controller;

import Server.controller.interfaces.auction.IAuctionController;
import exception.*;
import model.Auction;
import org.springframework.web.bind.annotation.*;
import repository.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
public class AuctionController {
    private AuctionRepository auctionRepository;

    public AuctionController() {
        auctionRepository = (AuctionRepository) RepositoryContainer.getInstance().getRepository("AuctionRepository");

    }

    @GetMapping("/controller/method/request/getAll-Auction/{token}")
    public List<Auction> getAllAuction(@PathVariable("token") String token) {
        return null;
    }

    @GetMapping("/controller/method/request/getAll-Auction-Customer/{token}")
    public List<Auction> getAuctionForCustomer(@PathVariable("token") String token) throws NotLoggedINException {
        return null;
    }

    @GetMapping("/controller/method/request/getAll-Auction-Customer/{token}")
    public Auction getAuctionForSeller(@PathVariable("token") String token) throws NotLoggedINException {
        return null;
    }

    @PostMapping("/controller/method/request/create-newAuction")
    public void createNewAuction(@RequestBody Map info) throws ObjectAlreadyExistException, NotLoggedINException, NotSellerException, InvalidTokenException {
        int productSellerId = (int) info.get("productSellerId");
        Date endDate = (Date) info.get("endDate");
        String token = (String) info.get("token");
    }

    @PostMapping("/controller/method/request/increase-price")
    public void increasePrice(@RequestBody Map info) throws InvalidIdException, NotLoggedINException, InvalidTokenException {
        int AuctionId = (int) info.get("AuctionId");
        long newPrice = (long) info.get("newPrice");
        String token = (String) info.get("token");
    }

    @PostMapping("/controller/method/request/participate-in-auction")
    public void participateInAuction(@RequestBody Map info) throws InvalidIdException, NotLoggedINException, InvalidTokenException, NotEnoughCreditException {
        int AuctionId = (int) info.get("AuctionId");
        long price = (long) info.get("price");
        String token = (String) info.get("token");
    }
}
