package repository;

import model.User;

public interface UserRepository extends Repository<User> {
    User getUserByUsername(String username);

    boolean doWeHaveAManager();

    boolean hasBoughtProduct(int customerId, int productId);
}
