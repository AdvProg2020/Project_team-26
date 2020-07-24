package Server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import exception.*;
import model.*;
import model.enums.Role;
import org.springframework.web.bind.annotation.*;
import repository.AuctionRepository;
import repository.ProductSellerRepository;
import repository.RepositoryContainer;

import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
public class AuctionController {

    private AuctionRepository auctionRepository;
    private ProductSellerRepository productSellerRepository;

    public AuctionController() {
        auctionRepository = (AuctionRepository) RepositoryContainer.getInstance().getRepository("AuctionRepository");
        productSellerRepository = (ProductSellerRepository) RepositoryContainer.getInstance().getRepository("ProductSellerRepository");
    }

    @GetMapping("/controller/method/request/getAll-Auction/{token}")
    public List<Auction> getAllAuction(@PathVariable("token") String token) {
        return auctionRepository.getAll();
    }


    @PostMapping("/controller/method/request/create-newAuction")
    public void createNewAuction(@RequestBody Map info) throws ObjectAlreadyExistException, NotLoggedINException, NotSellerException, InvalidTokenException, NoObjectIdException {
        int productSellerId = (int) info.get("productSellerId");
        Gson gson = new Gson();
        Date endDate = gson.fromJson((String)info.get("endDate"),Date.class);
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        ProductSeller productSeller = productSellerRepository.getById(productSellerId);
        if (user == null) {
            throw new NotLoggedINException("You must Be Logged in.");
        } else if (user.getRole() != Role.SELLER) {
            throw new NotSellerException("Only a Seller can start an auction.");
        } else if (productSeller == null) {
            throw new NoObjectIdException("The specified product does not exist.");
        } else if (auctionRepository.doesProductSellerExist(productSellerId)) {
            throw new ObjectAlreadyExistException("The specified object is already in an auction.", productSellerRepository.getById(productSellerId));
        } else {
            auctionRepository.save(new Auction(endDate, productSeller));
        }
    }

    @PostMapping("/controller/method/request/increase-price")
    public void increasePrice(@RequestBody Map info) throws InvalidIdException, NotLoggedINException, InvalidTokenException, NotCustomerException, NoAccessException {
        int AuctionId = (int) info.get("AuctionId");
        long newPrice = (long) info.get("newPrice");
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        Auction auction = auctionRepository.getById(AuctionId);
        if (user == null) {
            throw new NotLoggedINException("You Must be logged in.");
        } else if (user.getRole() != Role.CUSTOMER) {
            throw new NotCustomerException("Only customers can participate in auctions.");
        } else if (!auctionRepository.exist(AuctionId)) {
            throw new InvalidIdException("The specified auction does not exist.");
        } else if (auction.getPrice() <= newPrice) {
            throw new NoAccessException("The specified price is lower than the current price.");
        } else {
            auction.setPrice(newPrice);
            auction.setMaxSuggestedCustomer((Customer) user);
            auctionRepository.save(auction);
        }
    }

    @PostMapping("/controller/method/request/participate-in-auction")
    public void participateInAuction(@RequestBody Map info) throws InvalidIdException, NotLoggedINException, InvalidTokenException, NotCustomerException, NoAccessException {
        int auctionId = (int) info.get("auctionId");
        Gson gson = new Gson();
        Long price = gson.fromJson((String)info.get("price"),Long.class);
        long newPrice = price;
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        Auction auction = auctionRepository.getById(auctionId);
        if (user == null) {
            throw new NotLoggedINException("You Must be logged in.");
        } else if (user.getRole() != Role.CUSTOMER) {
            throw new NotCustomerException("Only customers can participate in auctions.");
        } else if (!auctionRepository.exist(auctionId)) {
            throw new InvalidIdException("The specified auction does not exist.");
        } else if (auction.getPrice() >= newPrice) {
            throw new NoAccessException("The specified price is lower than the current price.");
        } else {
            System.out.println("\n\n"+auctionId+"   : "+newPrice);
            auction.setPrice(newPrice);
            auction.setMaxSuggestedCustomer((Customer) user);
            auctionRepository.save(auction);
        }
    }
}
