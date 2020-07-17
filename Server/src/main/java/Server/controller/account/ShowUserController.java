package Server.controller.account;

import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NoObjectIdException;
import model.Admin;
import model.enums.Role;
import model.Session;
import model.User;
import org.springframework.web.bind.annotation.*;
import repository.RepositoryContainer;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ShowUserController {

    UserRepository userRepository;


    public ShowUserController() {

    }

    public ShowUserController(RepositoryContainer repositoryContainer) {
        this.userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
    }

    @PostMapping("/getUsers")
    public ArrayList<User> getUsers(@RequestBody String token) throws NoAccessException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        return (ArrayList<User>) userRepository.getAll();
    }

    @PostMapping("/getUserByName")
    public User getUserByName(@RequestBody String username,@RequestBody String token) throws NoAccessException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            return userRepository.getUserByUsername(username);
        }
    }

    @PostMapping("/getUserByName/{id}")
    public User getUserById(@PathVariable("id") int id, @RequestBody String token) throws NoAccessException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            return userRepository.getById(id);
        }
    }

    @PostMapping("/getUserInfo")
    public Map<String, String> getUserInfo(String token) throws NoAccessException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            return user.getDetails();
        }
    }

    @RequestMapping("/getManagers/{id}")
    public List<Admin> getManagers(@PathVariable("id") int id) {
        return userRepository.getManagers(id);
    }

    public void delete(String username, String token) throws NoAccessException, InvalidTokenException, NoObjectIdException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You are not allowed to do that.");
        } else
            userRepository.delete(getUserByName(username, token));
    }

    @PostMapping("/getUserByToken")
    public User getUserByToken(@RequestBody String token) throws InvalidTokenException {
        return Session.getSession(token).getLoggedInUser();
    }

}
