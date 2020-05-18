package controller.order;

import controller.interfaces.order.IOrderController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NoObjectIdException;
import model.*;
import repository.OrderRepository;
import repository.RepositoryContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class OrderController implements IOrderController {
    OrderRepository orderRepository;

    public OrderController(RepositoryContainer repositoryContainer) {
        this.orderRepository = (OrderRepository) repositoryContainer.getRepository("OrderRepository");
    }

    public List<Order> getOrders(String token) throws NoAccessException, InvalidTokenException {
        Session userSession = Session.getSession(token);
        if (userSession.getLoggedInUser() == null) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            switch (userSession.getLoggedInUser().getRole()) {
                case SELLER:
                    return (ArrayList<Order>) orderRepository.getAllSellerOrders(userSession.getLoggedInUser().getId());
                case CUSTOMER:
                    return (ArrayList<Order>) orderRepository.getAllCustomerOrders(userSession.getLoggedInUser().getId());
            }
            return null;
        }
    }

    @Override
    public List<Order> getOrdersWithFilter(String sortField, boolean isAcsending, String token) throws NoAccessException, InvalidTokenException {
        return null;
    }

    @Override
    public List<User> getProductBuyerByProductId(int productId, String token) {
        // TODO: get a List of users who bough this product, check if product belongs to the logged in seller
        return null;
    }


    public Order getASingleOrder(int id, String token) throws NoAccessException, NoObjectIdException, InvalidTokenException {
        Session userSession = Session.getSession(token);
        if (userSession.getLoggedInUser() == null) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            Order wantedOrder = orderRepository.getById(id);
            if (wantedOrder == null) {
                throw new NoObjectIdException("Object does not exist.");
            } else {
                switch (userSession.getLoggedInUser().getRole()) {
                    case CUSTOMER:
                        return getSingleCustomerOrder((Customer) userSession.getLoggedInUser(), wantedOrder);
                    case SELLER:
                        return getSingleSellerOrder((Seller) userSession.getLoggedInUser(), wantedOrder);
                }
            }
            return null;
        }
    }

    private Order getSingleCustomerOrder(Customer customer, Order wantedOrder) throws NoAccessException {
        if (!wantedOrder.getCustomer().getUsername().equals(customer.getUsername())) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            return orderRepository.getById(wantedOrder.getId());
        }
    }

    private Order getSingleSellerOrder(Seller seller, Order wantedOrder) {
        return (Order) orderRepository.getASingleSellerOrder(seller,wantedOrder);
    }



}
