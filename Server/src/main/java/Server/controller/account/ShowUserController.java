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
import java.util.HashMap;
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
    public ArrayList<User> getUsers(@RequestBody Map info){
        String token = (String) info.get("token");
        return (ArrayList<User>) userRepository.getAll();
    }

    @PostMapping("/getUserByName")
    public User getUserByName(@RequestBody Map info) throws NoAccessException, InvalidTokenException {
        String username = (String) info.get("username");
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            return userRepository.getUserByUsername(username);
        }
    }

    @PostMapping("/getUserByName")
    public User getUserById(@RequestBody Map info) throws NoAccessException, InvalidTokenException {
        int id = (Integer) info.get("id");
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            return userRepository.getById(id);
        }
    }

    @PostMapping("/getUserInfo")
    public Map<String, String> getUserInfo(@RequestBody Map info) throws NoAccessException, InvalidTokenException {
        String token = (String) info.get("token");
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

    @PostMapping("/user/delete")
    public void delete(@RequestBody Map info) throws NoAccessException, InvalidTokenException, NoObjectIdException {
        String username = (String)info.get("username");
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            Map<String,Object> userInfo = new HashMap<>();
            info.put("username",username);
            info.put("token",token);
            userRepository.delete(getUserByName(userInfo));
        }
    }

    @PostMapping("/getUserByToken")
    public User getUserByToken(@RequestBody Map info) throws InvalidTokenException {
        String token = (String) info.get("token");
        return Session.getSession(token).getLoggedInUser();
    }

}
