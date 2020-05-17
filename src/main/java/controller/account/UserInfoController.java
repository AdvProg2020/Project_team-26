package controller.account;

import controller.interfaces.account.IUserInfoController;
import exception.*;
import model.Role;
import model.Seller;
import model.Session;
import repository.RepositoryContainer;
import repository.UserRepository;

public class UserInfoController implements IUserInfoController {

    private UserRepository userRepository;


    public UserInfoController(RepositoryContainer repositoryContainer) {
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
    }


    public void changePassword(String oldPassword, String newPassword, String token) throws InvalidTokenException, NoAccessException, NotLoggedINException {
        Session session = Session.getSession(token);
        if (session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in.");
        } else {
            session.getLoggedInUser().changePassword(oldPassword, newPassword);
        }
    }

    public void changeInfo(String key, String value, String token) throws NotLoggedINException, InvalidTokenException, InvalidAuthenticationException, InvalidFormatException, NoSuchField {
        Session session = Session.getSession(token);
        if (session.getLoggedInUser() == null) {
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
        Session session = Session.getSession(token);
        if (session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in.");
        } else if (userRepository.getUserByUsername(value) != null) {
            throw new InvalidAuthenticationException("Username is already taken.", "Username");
        } else {
            session.getLoggedInUser().changeUsername(value);
        }
    }

    private void changeEmail(String value, String token) throws InvalidTokenException, InvalidFormatException {
        Session session = Session.getSession(token);
        if (!value.matches("^\\S+@\\S+.(?i)com(?-i)")) {
            throw new InvalidFormatException("Email format is incorrect.", "Email");
        } else {
            session.getLoggedInUser().changeEmail(value);
        }
    }

    private void changeName(String value, String token, int state) throws InvalidTokenException {
        Session session = Session.getSession(token);
        if (state == 1) {
            session.getLoggedInUser().changeFirstName(value);
        } else {
            session.getLoggedInUser().changeLastName(value);
        }
    }

    private void changeCompanyName(String value, String token) throws NoSuchField, InvalidTokenException {
        Session session = Session.getSession(token);
        if(session.getLoggedInUser().getRole() != Role.SELLER) {
            throw new NoSuchField("No Such Field exists.");
        } else {
            ((Seller) session.getLoggedInUser()).changeCompanyName(value);
        }
    }

    @Override
    public String getCompanyName(String token) throws InvalidTokenException, NotLoggedINException, NoSuchField {
        Session session = Session.getSession(token);
        if (session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not Logged in.");
        } else if (session.getLoggedInUser().getRole() != Role.SELLER) {
            throw new NoSuchField("This field does not exist.");
        } else {
            return ((Seller) session.getLoggedInUser()).getCompanyName();
        }
    }

    @Override
    public String getBalance(String token) throws NotLoggedINException, InvalidTokenException {
        Session session = Session.getSession(token);
        if (session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in");
        } else {
            return String.valueOf(session.getLoggedInUser().getCredit());
        }
    }

    public String getRole(String token) throws NotLoggedINException, InvalidTokenException {
        Session session = Session.getSession(token);
        if (session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in");
        } else {
            return session.getLoggedInUser().getRole().toString();
        }
    }
}
