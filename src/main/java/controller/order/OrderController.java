package controller.order;

import controller.interfaces.order.IOrderController;
import exception.NoAccessException;
import exception.NoObjectWithIdException;
import model.Customer;
import model.Order;
import model.Seller;
import model.Session;
import model.repository.OrderRepository;
import model.repository.Repository;
import model.repository.RepositoryContainer;
import model.repository.UserRepository;

import java.util.ArrayList;

public class OrderController implements IOrderController {
    OrderRepository orderRepository;
    public OrderController(RepositoryContainer repositoryContainer) {
        this.orderRepository = (OrderRepository) repositoryContainer.getRepository("OrderRepository");
    }
    public ArrayList<Order> getOrders(String token) throws NoAccessException {
        Session userSession = Session.getSession(token);
        if(userSession.getLoggedInUser() == null) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            switch (userSession.getLoggedInUser().getRole()) {
                case SELLER: return (ArrayList<Order>) orderRepository.getAllSellerOrders(userSession.getLoggedInUser().getId());
                case CUSTOMER: return (ArrayList<Order>) orderRepository.getAllCustomerOrders(userSession.getLoggedInUser().getId());
            }
            return null;
        }
    }


    public Order getASingleOrder(int id, String token) throws NoAccessException, NoObjectWithIdException {
        Session userSession = Session.getSession(token);
        if(userSession.getLoggedInUser() == null) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            Order wantedOrder = orderRepository.getById(id);
            if(wantedOrder == null) {
                throw new NoObjectWithIdException("Object does not exist.");
            } else {
                switch (userSession.getLoggedInUser().getRole()) {
                    case CUSTOMER: return getSingleCustomerOrder((Customer) userSession.getLoggedInUser(),wantedOrder);
                    case SELLER: return getSingleSellerOrder((Seller) userSession.getLoggedInUser(),wantedOrder);
                }
            }
            return null;
        }
    }

    private Order getSingleCustomerOrder(Customer customer, Order wantedOrder) throws NoAccessException {
        if(!wantedOrder.getCustomer().getUsername().equals(customer.getUsername())) {
            throw new NoAccessException("You are not allowed to do that.");
        }else {
            return orderRepository.getById(wantedOrder.getId());
        }
    }

    private Order getSingleSellerOrder(Seller seller, Order wantedOrder) {
        return (Order) orderRepository.getASingleSellerOrder(seller,wantedOrder);
    }


}
