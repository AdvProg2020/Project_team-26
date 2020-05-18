package repository.fake;

import exception.NoObjectIdException;
import model.Customer;
import model.Order;
import model.Seller;
import repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

public class FakeOrderRepository implements OrderRepository {

    private List<Order> allOrders;
    private int lastId = 0;
    private FakeUserRepository fakeUserRepository;

    public FakeOrderRepository() {
        fakeUserRepository = new FakeUserRepository();
        allOrders = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            save(new Order());
        }
    }

    @Override
    public List<Order> getAllCustomerOrders(int customerId) {
        return null;
    }

    @Override
    public List<Order> getAllSellerOrders(int sellerId) {
        return null;
    }


    @Override
    public List<Order> getAll() {
        return allOrders;
    }

    @Override
    public Order getById(int id) {
        for (Order order : allOrders) {
            if(order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    @Override
    public void save(Order object) {
        lastId++;
        object.setId(lastId);
        object.setCustomer((Customer) fakeUserRepository.getUserByUsername("test8"));
        allOrders.add(object);
    }

    @Override
    public void delete(int id) throws NoObjectIdException {

    }

    @Override
    public void delete(Order object) throws NoObjectIdException {

    }

    @Override
    public boolean exist(int id) {
        return false;
    }

    @Override
    public List<Order> getAllSorted(String sortField, boolean isAscending) {
        return null;
    }
}
