package controller.order;

import controller.interfaces.order.IOrderController;
import exception.*;
import model.*;
import repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class OrderController implements IOrderController {
    OrderRepository orderRepository;
    ProductRepository productRepository;
    CustomerRepository customerRepository;

    public OrderController(RepositoryContainer repositoryContainer) {
        this.orderRepository = (OrderRepository) repositoryContainer.getRepository("OrderRepository");
        this.productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
        this.customerRepository = (CustomerRepository) repositoryContainer.getRepository("CustomerRepository");
    }

    public List<Order> getOrders(String token) throws NoAccessException, InvalidTokenException {
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

    @Override
    public List<Order> getOrdersWithFilter(String sortField, boolean isAscending, int startIndex, int endIndex, String token) throws NoAccessException, InvalidTokenException, NotLoggedINException {
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

    @Override
    public List<Customer> getProductBuyerByProductId(int productId, String token) throws InvalidTokenException, NotLoggedINException, NoAccessException, InvalidIdException {
        Product product = productRepository.getById(productId);
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You must be Logged in to do this.");
        } else if (user.getRole() != Role.SELLER) {
            throw new NoAccessException("You must be a seller to view buyers of a product.");
        } else if (product == null) {
            throw new InvalidIdException("The specified product does not exist.");
        } else if (!doesHeOwnTheProduct(product,(Seller)user)) {
            throw new NoAccessException("You don't own this product.");
        } else {
            return customerRepository.getAllProductBuyers(productId,null);
        }
    }

    private boolean doesHeOwnTheProduct(Product product, Seller seller) {
        return product.getSellerList().stream().anyMatch(e -> e.getSeller().equals(seller));
    }


    public Order getASingleOrder(int id, String token) throws NoAccessException, NoObjectIdException, InvalidTokenException {
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

    private Pageable createAPage(String sortField, boolean isAscending, int startIndex, int endIndex) {
        if(isAscending) {
            return new Pageable(startIndex,endIndex - startIndex,sortField, Pageable.Direction.ASCENDING);
        } else {
            return new Pageable(startIndex,endIndex - startIndex,sortField, Pageable.Direction.DESCENDING);
        }
    }


}
