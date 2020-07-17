package Server.controller.account;

import exception.*;
import model.enums.Role;
import model.Seller;
import model.Session;
import model.User;
import org.hibernate.annotations.GeneratorType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import repository.RepositoryContainer;
import repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserInfoController {

    private UserRepository userRepository;
    private List<String> allFields;


    public UserInfoController(RepositoryContainer repositoryContainer) {
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
        allFields = Arrays.asList("Username", "FirstName", "LastName", "Email", "Company Name", "Balance");
    }


    @PostMapping("/changePassword")
    public void changePassword(@RequestBody String oldPassword, @RequestBody String newPassword, @RequestBody String token) throws InvalidTokenException, NoAccessException, NotLoggedINException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not logged in.");
        } else {
            user.changePassword(oldPassword, newPassword);
            userRepository.save(user);
        }
    }

    @PostMapping("/changePassword")
    public void changePassword(@RequestBody String newPassword, @RequestBody String token) throws InvalidTokenException, NotLoggedINException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not Logged In.");
        } else {
            user.changePassword(newPassword);
            userRepository.save(user);
        }
    }

    @PostMapping("/changeImage")
    public void changeImage(@RequestBody byte[] image,@RequestBody String token) throws InvalidTokenException, NotLoggedINException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not logged in.");
        }
        user.setImage(image);
        userRepository.save(user);
    }

    @PostMapping("/changeInfo")
    public void changeInfo(@RequestBody String key, @RequestBody String value, @RequestBody String token) throws NotLoggedINException, InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, NoSuchField {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not logged in.");
        } else {
            switch (key) {
                case "Username":
                    changeUsername(value, token);
                    break;
                case "FirstName":
                    changeName(value, token, 1);
                    break;
                case "LastName":
                    changeName(value, token, 2);
                    break;
                case "Email":
                    changeEmail(value, token);
                    break;
                case "Company Name":
                    changeCompanyName(value, token);
                    break;
                default:
                    throw new NoSuchField("No Such Field exists");
            }
            userRepository.save(user);
        }
    }

    @PostMapping("/changeBalance")
    public void changeBalance(@RequestBody Long newCredit, @RequestBody String token) throws InvalidTokenException, NotLoggedINException, InvalidAuthenticationException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not logged in.");
        } else {
            user.changeCredit((newCredit));
        }
    }

    private void changeUsername(String value, String token) throws InvalidTokenException, NotLoggedINException, InvalidAuthenticationException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not logged in.");
        } else if (userRepository.getUserByUsername(value) != null) {
            throw new InvalidAuthenticationException("Username is already taken.", "Username");
        } else {
            user.changeUsername(value);
        }
    }

    private void changeEmail(String value, String token) throws InvalidTokenException, InvalidFormatException {
        User user = Session.getSession(token).getLoggedInUser();
        if (!value.matches("^\\S+@\\S+.(?i)com(?-i)")) {
            throw new InvalidFormatException("Email format is incorrect.", "Email");
        } else {
            user.changeEmail(value);
            userRepository.save(user);
        }
    }

    private void changeName(String value, String token, int state) throws InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if (state == 1) {
            user.changeFirstName(value);
        } else {
            user.changeLastName(value);
        }
    }

    private void changeCompanyName(String value, String token) throws NoSuchField, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user.getRole() != Role.SELLER) {
            throw new NoSuchField("No Such Field exists.");
        } else {
            ((Seller) user).changeCompanyName(value);
        }
    }

    @PostMapping("/getCompanyName")
    public String getCompanyName(@RequestBody String token) throws InvalidTokenException, NotLoggedINException, NoSuchField {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not Logged in.");
        } else if (user.getRole() != Role.SELLER) {
            throw new NoSuchField("This field does not exist.");
        } else {
            return ((Seller) user).getCompanyName();
        }
    }

    @PostMapping("/changeInfo")
    public void changeInfo(@RequestBody Map<String, String> values, @RequestBody String token) throws NotLoggedINException, InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, NoSuchField {
        if (values.keySet().stream().anyMatch(n -> !allFields.contains(n))) {
            NoSuchField noSuchField = new NoSuchField("One or more fields is wrong");
            noSuchField.addAFields(values.keySet().stream().filter(field -> !allFields.contains(field))
                    .collect(Collectors.toList()));
            throw noSuchField;
        } else {
            for (Map.Entry<String, String> pair : values.entrySet()) {
                changeInfo(pair.getKey(), pair.getValue(), token);
            }
            userRepository.save(Session.getSession(token).getLoggedInUser());
        }
    }

    @PostMapping("/getBalance")
    public String getBalance(@RequestBody String token) throws NotLoggedINException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else {
            return String.valueOf(user.getCredit());
        }
    }

    @PostMapping("/getRole")
    public String getRole(@RequestBody String token) throws NotLoggedINException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else {
            return user.getRole().toString();
        }
    }
}
