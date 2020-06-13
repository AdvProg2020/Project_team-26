package controller.account;

import controller.interfaces.account.IUserInfoController;
import exception.*;
import model.Role;
import model.Seller;
import model.Session;
import model.User;
import repository.RepositoryContainer;
import repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserInfoController implements IUserInfoController {

    private UserRepository userRepository;
    private List<String> allFields;


    public UserInfoController(RepositoryContainer repositoryContainer) {
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
        allFields = Arrays.asList("Username", "FirstName", "LastName", "Email", "Company Name");
    }


    public void changePassword(String oldPassword, String newPassword, String token) throws InvalidTokenException, NoAccessException, NotLoggedINException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not logged in.");
        } else {
            user.changePassword(oldPassword, newPassword);
        }
    }

    public void changeInfo(String key, String value, String token) throws NotLoggedINException, InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, NoSuchField {
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

    @Override
    public String getCompanyName(String token) throws InvalidTokenException, NotLoggedINException, NoSuchField {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not Logged in.");
        } else if (user.getRole() != Role.SELLER) {
            throw new NoSuchField("This field does not exist.");
        } else {
            return ((Seller) user).getCompanyName();
        }
    }

    @Override
    public void changeInfo(Map<String, String> values, String token) throws NotLoggedINException, InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, NoSuchField {
        if (values.keySet().stream().anyMatch(n -> !allFields.contains(n))) {
            NoSuchField noSuchField = new NoSuchField("One or more fields is wrong");
            noSuchField.addAFields(values.keySet().stream().filter(field -> !allFields.contains(field))
                    .collect(Collectors.toList()));
            throw noSuchField;
        } else {
            for (Map.Entry<String, String> pair: values.entrySet()) {
                changeInfo(pair.getKey(),pair.getValue(),token);
            }
        }
    }

    @Override
    public String getBalance(String token) throws NotLoggedINException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else {
            return String.valueOf(user.getCredit());
        }
    }

    public String getRole(String token) throws NotLoggedINException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else {
            return user.getRole().toString();
        }
    }
}
