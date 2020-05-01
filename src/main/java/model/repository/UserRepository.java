package model.repository;

import model.User;

public interface UserRepository extends Repository<User> {

    public User getUserByName(String name);
    public boolean doWeHaveAManager();
}
