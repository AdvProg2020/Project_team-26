package controller.account;

import controller.interfaces.account.IShowUserController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NoObjectIdException;
import model.Admin;
import model.enums.Role;
import model.Session;
import model.User;
import repository.RepositoryContainer;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShowUserController implements IShowUserController {

    UserRepository userRepository;

    public ShowUserController(RepositoryContainer repositoryContainer) {
        this.userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
    }

    public ArrayList<User> getUsers(String token) throws NoAccessException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null || user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            return (ArrayList<User>) userRepository.getAll();
        }
    }

    public User getUserByName(String username, String token) throws NoAccessException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null || user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            return userRepository.getUserByUsername(username);
        }
    }

    @Override
    public User getUserById(int id, String token) throws NoAccessException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null || user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            return userRepository.getById(id);
        }
    }

    public Map<String, String> getUserInfo(String token) throws NoAccessException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            return user.getDetails();
        }
    }

    public List<Admin> getManagers(int id) {
        return userRepository.getManagers(id);
    }

    public void delete(String username, String token) throws NoAccessException, InvalidTokenException, NoObjectIdException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You are not allowed to do that.");
        } else
            userRepository.delete(getUserByName(username,token));
    }

    public User getUserByToken(String token) throws InvalidTokenException {
        return Session.getSession(token).getLoggedInUser();
    }

}
