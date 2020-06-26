package repository;

import model.Admin;
import model.User;

import java.util.List;

public interface UserRepository extends Repository<User> {
    User getUserByUsername(String username);

    boolean doWeHaveAManager();

    boolean hasBoughtProduct(int customerId, int productId);

    List<Admin> getManagers(int id);

    User getUserByEmail(String email);
}
