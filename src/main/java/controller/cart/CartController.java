package controller.cart;

import controller.interfaces.cart.ICartController;
import exception.*;
import model.*;
import model.enums.ShipmentState;
import repository.*;
import view.gui.Constants;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

public class CartController implements ICartController {

    private ProductSellerRepository productSellerRepository;
    private PromoRepository promoRepository;
    private UserRepository userRepository;
    private OrderRepository orderRepository;

    public CartController(RepositoryContainer repositoryContainer) {
        productSellerRepository = (ProductSellerRepository) repositoryContainer.getRepository("ProductSellerRepository");
        promoRepository = (PromoRepository) repositoryContainer.getRepository("PromoRepository");
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
        orderRepository = (OrderRepository) repositoryContainer.getRepository("OrderRepository");
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

        Customer customer = (Customer) userRepository.getById(loggedInUser.getId());
        if (!customer.getAvailablePromos().stream().anyMatch(usedPromo -> promo.equals(usedPromo))) {
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
        customer.pay(order.calculatePaidAmount() - order.calculateDiscount());
        order.setDiscount();
        changeSellerCredit(order);
        order.calculatePaidAmount();
        orderRepository.save(order);
        if (order.calculatePaidAmount() > 500000) {
            creatRandomPromo(customer);
        }
    }

    public void changeSellerCredit(Order order) {
        for (OrderItem item : order.getItems()) {
            long money = item.getPaidPrice() * item.getAmount();
            item.getSeller().setCredit(item.getSeller().getCredit() + money);
        }
    }

    private void creatRandomPromo(Customer customer) {
        Promo promo = new Promo();
        promo.getCustomers().add(customer);
        promo.setMaxValidUse(1);
        promo.setMaxDiscount(50000);
        promo.setPercent(10);
        Date startDate = new Date();
        promo.setStartDate(startDate);
        Date endDate;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Random r = new Random();
        int year = r.nextInt(2030 - 2022) + 2022;
        int month = r.nextInt(11 - 1) + 1;
        try {
            endDate = formatter.parse("20-" + month + "-" + year + " 8:00:00");
            promo.setEndDate(endDate);
            promo.setPromoCode(customer.getUsername().substring(0, (customer.getUsername().length() + 1) / 2) + new Date().getTime());
            if (promoRepository.getByCode(promo.getPromoCode()) == null) {
                promoRepository.save(promo);
                Constants.manager.showSuccessPopUp("promo: " + promo.getPromoCode());
            }
        } catch (ParseException | IOException e) {
            e.getStackTrace();
        }
    }

    private Order createOrder(Cart cart, Customer customer) throws NotEnoughProductsException {
        Order order = new Order(customer, cart.getUsedPromo(), cart.getAddress());

        for (ProductSeller productSeller : cart.getProducts().keySet()) {
            if (productSeller.getRemainingItems() <= cart.getProducts().get(productSeller)) {
                throw new NotEnoughProductsException("There is not enough products anymore.", productSeller);
            }
            OrderItem orderItem = new OrderItem(productSeller.getProduct(),
                    cart.getProducts().get(productSeller),
                    productSeller.getSeller(),
                    productSeller.getPrice(), productSeller.getPriceInOff(),
                    ShipmentState.WAITING_TO_SEND);

            orderItem.setOrder(order);
            order.addItem(orderItem);
        }

        processOrder(cart.getProducts());
        return order;
    }

    private void processOrder(Map<ProductSeller, Integer> orderItems) throws NotEnoughProductsException {
        for (ProductSeller productSeller : orderItems.keySet()) {
            productSeller.sell(orderItems.get(productSeller));
            productSellerRepository.save(productSeller);
        }
    }

    public long getTotalPrice(Cart cart, String token) throws InvalidTokenException {
        long totalPrice = 0;
      /*  cart.getProducts().forEach((key, value) -> {
            totalPrice += key.getPriceInOff() * value;
        });*/
        for (Map.Entry<ProductSeller, Integer> productSeller : cart.getProducts().entrySet()) {
            totalPrice += productSeller.getKey().getPriceInOff() * productSeller.getValue();
        }
        return totalPrice;
    }

    @Override
    public int getAmountInCartBySellerId(int productSelleId, String token) throws InvalidTokenException, NoSuchObjectException {
        Session session = Session.getSession(token);
        for (ProductSeller productSeller : session.getCart().getProducts().keySet()) {
            if (productSeller.getId() == productSelleId) {
                return session.getCart().getProducts().get(productSeller);
            }
        }
        throw new NoSuchObjectException("No Object with specified details exist");
    }
}