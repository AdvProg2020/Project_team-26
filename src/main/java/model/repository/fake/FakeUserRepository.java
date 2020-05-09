package model.repository.fake;

import controller.account.Account;
import model.*;
import model.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FakeUserRepository implements UserRepository {
    List<User> allUsers;
    List<Account> allFakeAccounts;
    static int lastId = 5;

    public FakeUserRepository() {
        allUsers = new ArrayList<>();
        allFakeAccounts = new ArrayList<>();
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
    }

    @Override
    public User getUserByName(String userName) {
        List<User> users = allUsers.stream().filter(user -> user.getUsername().equals(userName)).collect(Collectors.toList());
        if (users.size() == 0)
            return null;
        return users.get(0);
    }

    @Override
    public boolean doWeHaveAManager() {
        if (allUsers.stream().filter(user -> user.getRole() == Role.ADMIN).collect(Collectors.toList()).size() == 0)
            return true;
        return false;
    }

    @Override
    public boolean hasBoughtProduct(int customerId, int productId) {
        // FakeProductRepository


        return false;
    }

    @Override
    public List<User> getAll() {
        return allUsers;
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
            System.out.println(object.getUsername() + " " + object.getRole());
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
}
