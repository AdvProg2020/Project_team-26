package Server.controller.order;

import com.fasterxml.jackson.core.PrettyPrinter;
import exception.*;
import model.*;
import model.enums.Role;
import org.springframework.web.bind.annotation.*;
import repository.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {
    OrderRepository orderRepository;
    ProductRepository productRepository;
    CustomerRepository customerRepository;

    public OrderController() {

    }


    public OrderController(RepositoryContainer repositoryContainer) {
        this.orderRepository = (OrderRepository) repositoryContainer.getRepository("OrderRepository");
        this.productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
        this.customerRepository = (CustomerRepository) repositoryContainer.getRepository("CustomerRepository");
    }


    @PostMapping("/controller/method/order/get-orders")
    public List<Order> getOrders(@RequestBody Map info) throws NoAccessException, InvalidTokenException {
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            switch (user.getRole()) {
                case SELLER:
                    return (ArrayList<Order>) orderRepository.getAllSellerOrders(user.getId(), null);
                case CUSTOMER:
                    return (ArrayList<Order>) orderRepository.getAllCustomerOrders(user.getId(), null);
            }
            return null;
        }
    }

    @PostMapping("/controller/method/order/get-orders-with-filters")
    public List<Order> getOrdersWithFilter(@RequestBody Map info) throws NoAccessException, InvalidTokenException, NotLoggedINException {
        String sortField = (String) info.get("sortField");
        boolean isAscending = (Boolean) info.get("isAscending");
        int startIndex = (Integer) info.get("startIndex");
        int endIndex = (Integer) info.get("endIndex");
        String token = (String) info.get("token");
        Pageable page =  createAPage(sortField,isAscending,startIndex,endIndex);
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You must be logged in");
        } else if(user.getRole() != Role.CUSTOMER) {
            throw new NoAccessException("You must be a customer to gte Orders");
        } else {
            List<Order> allOrders  = new ArrayList<>();
            for (Order order : orderRepository.getAll(page)) {
                if(order.getCustomer().getUsername().equals(user.getUsername())) {
                    allOrders.add(order);
                }
            }
            return allOrders;
        }
    }

    @PostMapping("/controller/method/order/get-product-buyer-by-product-id")
    public List<Customer> getProductBuyerByProductId(@RequestBody Map info) throws InvalidTokenException, NotLoggedINException, NoAccessException, InvalidIdException {
        int productId = (Integer) info.get("productId");
        String token = (String) info.get("token");
        Product product = productRepository.getById(productId);
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You must be Logged in to do this.");
        } else if (user.getRole() != Role.SELLER) {
            throw new NoAccessException("You must be a seller to view buyers of a product.");
        } else if (product == null) {
            throw new InvalidIdException("The specified product does not exist.");
        } else if (productRepository.getProductBySellerId(productId, user.getId()) == null) {
            throw new NoAccessException("You don't own this product.");
        } else {
            return customerRepository.getAllProductBuyers(productId,null);
        }
    }

    @PostMapping("/controller/method/order/get-a-single-order")
    public Order getASingleOrder(@RequestBody Map info) throws NoAccessException, NoObjectIdException, InvalidTokenException {
        int id = (Integer) info.get("id");
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            Order wantedOrder = orderRepository.getById(id);
            if (wantedOrder == null) {
                throw new NoObjectIdException("Object does not exist.");
            } else {
                switch (user.getRole()) {
                    case CUSTOMER:
                        return getSingleCustomerOrder((Customer) user, wantedOrder);
                    case SELLER:
                        return getSingleSellerOrder((Seller) user, wantedOrder);
                }
            }
            return null;
        }
    }

    @PostMapping("/controller/method/order/get-order-history-for-seller")
    public List<Order> getOrderHistoryForSeller(@RequestBody Map info) throws NoAccessException, InvalidTokenException, NotLoggedINException {
        String sortField = (String) info.get("sortField");
        boolean isAscending = (Boolean) info.get("isAscending");
        int startIndex = (Integer) info.get("startIndex");
        int endIndex = (Integer) info.get("endIndex");
        String token = (String) info.get("token");
        Pageable page = createAPage(sortField,isAscending,startIndex,endIndex);
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You must be Logged in.");
        } else if (user.getRole() != Role.SELLER) {
            throw new NoAccessException("Only Seller");
        } else {
            return orderRepository.getAllSellerOrders(user.getId(),page);
        }
    }

    private Order getSingleCustomerOrder(Customer customer, Order wantedOrder) throws NoAccessException {
        if (!wantedOrder.getCustomer().getUsername().equals(customer.getUsername())) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            return orderRepository.getById(wantedOrder.getId());//todo correct
        }
    }

    private Order getSingleSellerOrder(Seller seller, Order wantedOrder) throws NoAccessException {
        if (!wantedOrder.getCustomer().getUsername().equals(seller.getUsername())) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            return orderRepository.getById(wantedOrder.getId());
        }
    }

    @PostMapping("/controller/method/get-order-items-with-order-id")
    public List<OrderItem> getOrderItemsWithOrderId(@RequestBody Map info) throws InvalidTokenException, NoAccessException {
        User user = Session.getSession((String) info.get("token")).getLoggedInUser();
        int orderId = (int) info.get("orderId");
        switch (user.getRole()) {
            case SELLER:
                return orderRepository.getSellerOrderItems(user.getId(),orderId);
            case CUSTOMER:
                return orderRepository.getById(orderId).getItems();
            default:
                throw new NoAccessException("You are not allowed to do that.");
        }
    }

    @RequestMapping("/controller/method/get-order-for-seller/(id}")
    public List<Order> getOrderItemsForSeller(@PathVariable("id") int id) {
        return orderRepository.getAllSellerOrders(id,null);
    }

    private Pageable createAPage(String sortField, boolean isAscending, int startIndex, int endIndex) {
        if(isAscending) {
            return new Pageable(startIndex,endIndex - startIndex,sortField, Pageable.Direction.ASCENDING);
        } else {
            return new Pageable(startIndex,endIndex - startIndex,sortField, Pageable.Direction.DESCENDING);
        }
    }
}
