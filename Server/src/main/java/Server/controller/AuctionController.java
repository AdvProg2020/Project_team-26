package Server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import exception.*;
import model.*;
import model.enums.Role;
import model.enums.ShipmentState;
import org.springframework.web.bind.annotation.*;
import repository.AuctionRepository;
import repository.OrderRepository;
import repository.ProductSellerRepository;
import repository.RepositoryContainer;

import java.util.*;


@RestController
public class AuctionController {

    private AuctionRepository auctionRepository;
    private ProductSellerRepository productSellerRepository;
    private OrderRepository orderRepository;

    public AuctionController() {
        auctionRepository = (AuctionRepository) RepositoryContainer.getInstance().getRepository("AuctionRepository");
        productSellerRepository = (ProductSellerRepository) RepositoryContainer.getInstance().getRepository("ProductSellerRepository");
        orderRepository = (OrderRepository) RepositoryContainer.getInstance().getRepository("OrderRepository");
    }

    @GetMapping("/controller/method/request/getAll-Auction/{token}")
    public List<Auction> getAllAuction(@PathVariable("token") String token) {
        return auctionRepository.getAll();
    }


    @PostMapping("/controller/method/request/create-newAuction")
    public void createNewAuction(@RequestBody Map info) throws ObjectAlreadyExistException, NotLoggedINException, NotSellerException, InvalidTokenException, NoObjectIdException {
        int productSellerId = (int) info.get("productSellerId");
        Gson gson = new Gson();
        Date endDate = gson.fromJson((String) info.get("endDate"), Date.class);
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
            Auction auction = new Auction(endDate, productSeller);
            auctionRepository.save(auction);
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Auction timePassesAuction = auctionRepository.getById(auction.getId());
                    if (timePassesAuction.getMaxSuggestedCustomer() == null) {
                        return;
                    }
                    Order order = new Order((Customer) timePassesAuction.getMaxSuggestedCustomer(), null, "...");
                    ProductSeller auctionProductSeller = timePassesAuction.getProductSeller();
                    if (auctionProductSeller.getRemainingItems() == 0) {
                        return;
                    }
                    OrderItem orderItem = new OrderItem(auctionProductSeller.getProduct(), 1, auctionProductSeller.getSeller(), timePassesAuction.getPrice(), timePassesAuction.getPrice(), ShipmentState.WAITING_TO_SEND);
                    orderItem.setOrder(order);
                    order.getItems().add(orderItem);
                    try {
                        auctionProductSeller.sell(1);
                    } catch (NotEnoughProductsException e) {
                        return;
                    }
                    productSellerRepository.save(auctionProductSeller);
                    try {
                        timePassesAuction.getMaxSuggestedCustomer().pay(timePassesAuction.getPrice());
                    } catch (NotEnoughCreditException e) {
                        return;
                    }
                    order.setDiscount();
                    OrderItem item = order.getItems().get(0);
                    item.getSeller().setCredit(item.getSeller().getCredit() + (long) (timePassesAuction.getPrice() * 1 - Session.getCommission() / 100));
                    order.calculatePaidAmount();
                    orderRepository.save(order);
                }
            };
            Timer timer = new Timer("Timer");
            long delay = endDate.getTime() - new Date().getTime();
            timer.schedule(task, delay);
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
        Long price = gson.fromJson((String) info.get("price"), Long.class);
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
            System.out.println("\n\n" + auctionId + "   : " + newPrice);
            auction.setPrice(newPrice);
            auction.setMaxSuggestedCustomer((Customer) user);
            auctionRepository.save(auction);
        }
    }
}
