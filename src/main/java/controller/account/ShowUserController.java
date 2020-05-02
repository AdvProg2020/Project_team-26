package controller.account;

import controller.interfaces.account.IShowUserController;
import exception.NoAccessException;
import model.Role;
import model.Session;
import model.User;
import model.repository.RepositoryContainer;
import model.repository.UserRepository;

import java.util.ArrayList;
import java.util.Map;

public class ShowUserController implements IShowUserController {

    UserRepository userRepository;

    public ShowUserController(RepositoryContainer repositoryContainer) {
        this.userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
    }

    public ArrayList<User> getUsers(String token) throws NoAccessException {
        Session userSession = Session.getSession(token);
        if(userSession.getLoggedInUser() == null || userSession.getLoggedInUser().getRole() != Role.ADMIN) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            return (ArrayList<User>) userRepository.getAll();
        }
    }

    public User getUserByName(String username, String token) throws NoAccessException {
        Session userSession = Session.getSession(token);
        if(userSession.getLoggedInUser() == null || userSession.getLoggedInUser().getRole() != Role.ADMIN) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            return userRepository.getUserByName(username);
        }
    }

    @Override
    public User getUserById(int id, String token) throws NoAccessException {
        Session userSession = Session.getSession(token);
        if(userSession.getLoggedInUser() == null || userSession.getLoggedInUser().getRole() != Role.ADMIN) {
            throw new NoAccessException("You are not allowed to do that");
        } else {
            return userRepository.getById(id);
        }
    }

    public Map<String, String> getUserInfo(String token) throws NoAccessException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            return user.getDetails();
        }
}

    public void delete(String username , String token) throws Exceptions.InvalidDeleteDemand, NoAccessException {
        User user = Session.getSession(token).getLoggedInUser();
        if(user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            userRepository.delete(user.getId());
        }
    }
}
