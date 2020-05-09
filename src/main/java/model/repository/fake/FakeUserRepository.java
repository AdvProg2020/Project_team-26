package model.repository.fake;

import controller.account.Account;
import model.Category;
import model.Role;
import model.User;
import model.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class FakeUserRepository implements UserRepository {
    List<User> allUsers;
    List<Account> allFakeAccounts;
    static int lastId = 5;

    public FakeUserRepository() {
        allUsers = new ArrayList<>();
        allFakeAccounts = new ArrayList<>();
        for (int n = 1 ; n < 11 ; n++) {
            Account account = new Account();
            account.setUsername("test" + (n));
            account.setPassword("password" + n);
            if (n < 4)
                account.setRole("manager");
            else if (n < 7)
                account.setRole("seller");
            else
                account.setRole("customer");
            allFakeAccounts.add(account);
        }
        for (int i = 0; i < 10; i++) {
            save(new User(allFakeAccounts.get(i)));
        }
    }

    @Override
    public User getUserByName(String userName) {
        return allUsers.stream().filter(user -> user.getUsername().equals(userName)).findAny().get();
    }

    @Override
    public boolean doWeHaveAManager() {
        if (allUsers.stream().filter(user -> user.getRole() == Role.ADMIN).findAny().get() != null)
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
        return allUsers.stream().filter(user -> user.getId() == id).findAny().get();
    }

    @Override
    public void save(User object) {
        lastId++;
        object.setId(lastId);
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
