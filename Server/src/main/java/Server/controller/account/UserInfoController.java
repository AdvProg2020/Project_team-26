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
import java.util.HashMap;
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


    @PostMapping("/controller/method/user/change-password")
    public void changePasswordWithOldPassword(@RequestBody Map newInfo) throws InvalidTokenException, NoAccessException, NotLoggedINException {
        String oldPassword = (String) newInfo.get("oldPassword");
        String newPassword = (String) newInfo.get("newPassword");
        String token = (String) newInfo.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not logged in.");
        } else {
            user.changePassword(oldPassword, newPassword);
            userRepository.save(user);
        }
    }

    @PostMapping("/controller/method/change-password-old-way")
    public void changePassword(@RequestBody Map oldInfo) throws InvalidTokenException, NotLoggedINException {
        String newPassword = (String) oldInfo.get("newPassword");
        String token = (String) oldInfo.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not Logged In.");
        } else {
            user.changePassword(newPassword);
            userRepository.save(user);
        }
    }

    @PostMapping("/controller/method/change-image")
    public void changeImage(@RequestBody Map info) throws InvalidTokenException, NotLoggedINException {
        byte[] image = (byte[]) info.get("image");
        String token = (String) info.get("token");

        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not logged in.");
        }
        user.setImage(image);
        userRepository.save(user);
    }

    @PostMapping("/controller/method/change-info")
    public void changeInfo(@RequestBody Map info) throws NotLoggedINException, InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, NoSuchField {
        String key = (String) info.get("key");
        String value = (String) info.get("value");
        String token = (String) info.get("token");
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

    @PostMapping("/controller/method/change-balance")
    public void changeBalance(@RequestBody Map info) throws InvalidTokenException, NotLoggedINException, InvalidAuthenticationException {
        Long newCredit = (Long) info.get("newCredit");
        String token = (String) info.get("token");
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

    @PostMapping("/controller/method/user/get-company-name")
    public String getCompanyName(@RequestBody Map info) throws InvalidTokenException, NotLoggedINException, NoSuchField {
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not Logged in.");
        } else if (user.getRole() != Role.SELLER) {
            throw new NoSuchField("This field does not exist.");
        } else {
            return ((Seller) user).getCompanyName();
        }
    }

    @PostMapping("/controller/method/user/change-multiple-info")
    public void changeMultipleInfo(@RequestBody Map info) throws InvalidTokenException, NoSuchField, InvalidFormatException, InvalidAuthenticationException, NotLoggedINException {
        Map<String, String> values = (Map<String, String>) info.get("values");
        String token = (String) info.get("token");
        if (values.keySet().stream().anyMatch(n -> !allFields.contains(n))) {
            NoSuchField noSuchField = new NoSuchField("One or more fields is wrong");
            noSuchField.addAFields(values.keySet().stream().filter(field -> !allFields.contains(field))
                    .collect(Collectors.toList()));
            throw noSuchField;
        } else {
            Map<String, String> userInfo = new HashMap<>();
            for (Map.Entry<String, String> pair : values.entrySet()) {
                userInfo.put("key", pair.getKey());
                userInfo.put("value", pair.getValue());
                userInfo.put("token", token);
                changeInfo(userInfo);
            }
            userRepository.save(Session.getSession(token).getLoggedInUser());
        }
    }

    @PostMapping("/controller/method/user/get-balance")
    public String getBalance(@RequestBody Map info) throws NotLoggedINException, InvalidTokenException {
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else {
            return String.valueOf(user.getCredit());
        }
    }

    @PostMapping("/controller/method/user/get-role")
    public String getRole(@RequestBody Map info) throws NotLoggedINException, InvalidTokenException {
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else {
            return user.getRole().toString();
        }
    }
}
