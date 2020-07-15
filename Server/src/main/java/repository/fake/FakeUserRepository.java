package repository.fake;

import Server.controller.account.Account;
import model.*;
import model.enums.Role;
import model.enums.ShipmentState;
import repository.Pageable;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FakeUserRepository implements UserRepository {
    List<User> allUsers;
    int lastId = 0;

    FakeProductRepository fakeProductRepository;

    public FakeUserRepository() {
        fakeProductRepository = new FakeProductRepository();
        allUsers = new ArrayList<>();
        List<Account> allFakeAccounts = new ArrayList<>();
        for (int n = 1; n < 11; n++) {
            Account account = new Account();
            account.setUsername("test" + (n));
            account.setPassword("password" + n);
            if (n < 4)
                account.setRole(Role.ADMIN);
            else if (n < 7)
                account.setRole(Role.SELLER);
            else
                account.setRole(Role.CUSTOMER);
            allFakeAccounts.add(account);
        }
        for (Account account : allFakeAccounts) {
            save(account.makeUser());
        }
        for (User user : allUsers) {
            if(user instanceof Customer) {
                Order order = new Order((Customer) user, new Promo("BigRetard",(Customer)user)," ");
                OrderItem orderItem = new OrderItem(fakeProductRepository.getById(1),2,(Seller) getById(6),200,
                        190, ShipmentState.WAITING_TO_SEND);
                order.addItem(orderItem);
                ((Customer) user).addOrder(order);
            }
        }
    }

    @Override
    public User getUserByUsername(String username) {
        List<User> users = allUsers.stream().filter(user -> user.getUsername().equals(username)).collect(Collectors.toList());
        if (users.size() == 0)
            return null;
        return users.get(0);
    }

    @Override
    public boolean doWeHaveAManager() {
        if (allUsers.stream().filter(user -> user.getRole() == Role.ADMIN).collect(Collectors.toList()).size() == 0)
            return false;
        return true;
    }

    @Override
    public boolean hasBoughtProduct(int customerId, int productId) {
        if(getById(customerId) instanceof Customer) {
            for (Order order : ((Customer) getById(customerId)).getOrders()) {
                for (OrderItem item : order.getItems()) {
                    if(item.getProductId() == productId) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    @Override
    public List<Admin> getManagers(int id) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return allUsers;
    }

    @Override
    public List<User> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public User getById(int id) {
        List<User> users = allUsers.stream().filter(user -> user.getId() == id).collect(Collectors.toList());
        if (users.size() == 0)
            return null;
        return users.get(0);
    }

    @Override
    public void save(User object) {
        if (object.getId() == 0) {
            lastId++;
            object.setId(lastId);
            allUsers.add(object);
            return;
        }
        User user = getById(object.getId());
        allUsers.remove(user);
        allUsers.add(object);
    }


    @Override
    public void delete(int id) {
        if (allUsers.contains(getById(id)))
            allUsers.remove(getById(id));
    }

    @Override
    public void delete(User object) {
        if (allUsers.contains(object))
            allUsers.remove(object);
    }

    @Override
    public boolean exist(int id) {
        return false;
    }

    @Override
    public List<User> getAllSorted(String sortField, boolean isAscending) {
        return null;
    }

}
