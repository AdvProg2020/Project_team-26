package controller.cart;

import interfaces.cart.ICartController;
import exception.*;
import model.*;
import repository.ProductSellerRepository;
import repository.PromoRepository;
import repository.RepositoryContainer;

import java.rmi.NoSuchObjectException;
import java.util.Map;

public class CartController implements ICartController {

    ProductSellerRepository productSellerRepository;
    PromoRepository promoRepository;

    public CartController(RepositoryContainer repositoryContainer) {
        productSellerRepository = (ProductSellerRepository) repositoryContainer.getRepository("ProductSellerRepository");
        promoRepository = (PromoRepository) repositoryContainer.getRepository("PromoRepository");
    }

    @Override
    public void addOrChangeProduct(int productSellerId, int amount, String token)
            throws InvalidIdException, NotEnoughProductsException, InvalidTokenException {
        Session session = Session.getSession(token);
        ProductSeller productSeller = productSellerRepository.getById(productSellerId);
        if (productSeller == null) {
            throw new InvalidIdException("There is no product seller with this id");
        }

        if (!session.getCart().addItems(productSeller, amount)) {
            throw new NotEnoughProductsException("There isn't enough products available", productSeller);
        }
    }

    @Override
    public Cart getCart(String token) throws InvalidTokenException {
        Session session = Session.getSession(token);
        return session.getCart();
    }

    @Override
    public void setAddress(String address, String token) throws InvalidTokenException {
        Session session = Session.getSession(token);
        session.getCart().setAddress(address);
    }

    @Override
    public void usePromoCode(String promoCode, String token) throws InvalidTokenException, InvalidPromoCodeException, PromoNotAvailableException, NotLoggedINException, NoAccessException {
        Session session = Session.getSession(token);
        User loggedInUser = session.getLoggedInUser();

        if (loggedInUser == null) {
            throw new NotLoggedINException("You must login before using promo code.");
        }

        if (!session.isUserCustomer()) {
            throw new NoAccessException("You must be a customer to use promo code.");
        }

        Promo promo = promoRepository.getByCode(promoCode);
        if (promo == null) {
            throw new InvalidPromoCodeException("There is no promo with this code.");
        }

        if (!promo.isAvailable()) {
            throw new PromoNotAvailableException("This promo is not available now.");
        }

        Customer customer = (Customer) loggedInUser;
        if (!customer.getAvailablePromos().stream().anyMatch(userPromo -> userPromo.equals(promo))) {
            throw new PromoNotAvailableException("This promo is not for you.");
        }

        long usedCount = customer.getOrders().stream().filter(order -> order.getUsedPromo().equals(promo)).count();
        if (usedCount >= promo.getMaxValidUse()) {
            throw new PromoNotAvailableException("You have used this promo before.");
        }
        session.getCart().setUsedPromo(promo);
    }

    @Override
    public void checkout(String token)
            throws InvalidTokenException, NotLoggedINException, NoAccessException, NotEnoughProductsException, NotEnoughCreditException {
        Session session = Session.getSession(token);
        User loggedInUser = session.getLoggedInUser();

        if (loggedInUser == null) {
            throw new NotLoggedINException("You must login before using promo code.");
        }

        if (!session.isUserCustomer()) {
            throw new NoAccessException("You must be a customer to be able to buy.");
        }

        Customer customer = (Customer) loggedInUser;
        Order order = createOrder(session.getCart(), customer);
        customer.pay(order.getPaidAmount());
        customer.addOrder(order);
    }

    private Order createOrder(Cart cart, Customer customer) throws NotEnoughProductsException {
        Order order = new Order(customer, cart.getUsedPromo(), cart.getAddress());

        for (ProductSeller productSeller : cart.getProducts().keySet()) {
            if (productSeller.getRemainingItems() < cart.getProducts().get(productSeller)) {
                throw new NotEnoughProductsException("There is not enough products anymore.", productSeller);
            }

            // TODO: process offs for paid price (Done)
            OrderItem orderItem = new OrderItem(productSeller.getProduct(),
                    cart.getProducts().get(productSeller),
                    productSeller.getSeller(),
                    productSeller.getPrice(), productSeller.getPriceInOff(),
                    ShipmentState.WAITING_TO_SEND);

            order.addItem(orderItem);
        }

        processOrder(cart.getProducts());
        return order;
    }

    private void processOrder(Map<ProductSeller, Integer> orderItems) throws NotEnoughProductsException {
        for (ProductSeller productSeller : orderItems.keySet()) {
            productSeller.sell(orderItems.get(productSeller));
        }
    }

    public long getToTalPrice(Cart cart, String token) throws InvalidTokenException {
        long totalPrice = 0;
        for (ProductSeller productSeller : cart.getProducts().keySet()) {
            totalPrice += productSeller.getPrice();
        }
        return totalPrice;
    }

    @Override
    public int getAmountInCartBySellerId(int productSelleId, String token) throws InvalidTokenException, NoSuchObjectException {
        Session session = Session.getSession(token);
        for (ProductSeller productSeller : session.getCart().getProducts().keySet()) {
            if(productSeller.getId() == productSelleId) {
                return session.getCart().getProducts().get(productSeller);
            }
        }
        throw new NoSuchObjectException("No Object with specified details exist");
    }
}