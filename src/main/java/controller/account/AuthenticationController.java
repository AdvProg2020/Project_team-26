package controller.account;

import controller.interfaces.account.IAuthenticationController;
import exception.*;
import model.Role;
import model.Session;
import model.User;
import model.repository.RepositoryContainer;
import model.repository.UserRepository;

import javax.naming.AuthenticationException;

public class AuthenticationController implements IAuthenticationController {

    UserRepository userRepository;

    public AuthenticationController(RepositoryContainer repositoryContainer) {
        this.userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
    }

    public void login(String username, String password, String token) throws InvalidFormatException, PasswordIsWrongException, InvalidTokenException, InvalidAuthenticationException {
        Session userSession = Session.getSession(token);
        checkPasswordFormat(password);
        checkUsernameFormat(username);
        checkUsernameAndPassword(username, password);
        userSession.login(userRepository.getUserByName(username));
    }

    public void register(Account account, String token) throws InvalidFormatException, NoAccessException, InvalidAuthenticationException, NoAccessException, InvalidTokenException {
        Session userSession = Session.getSession(token);
        checkPasswordFormat(account.getPassword());
        checkUsernameFormat(account.getUsername());
        checkUsernameAvailability(account.getUsername());
        if (userSession.getLoggedInUser() != null) {
            registerAdmin(account, token);
        } else {
            switch (account.getRole()) {
                case CUSTOMER:
                    registerCustomer(account, token);
                    break;
                case SELLER:
                    registerSeller(account, token);
                    break;
                case ADMIN:
                    registerAdmin(account, token);
            }
        }
    }

    public void logout(String token) throws NotLoggedINException, InvalidTokenException {
        Session userSession = Session.getSession(token);
        if (userSession.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in.");
        } else {
            userSession.logout();
        }
    }

    private void checkPasswordFormat(String password) throws InvalidFormatException {
        if (!password.matches("^[^\\s]+$")) {
            throw new InvalidFormatException("Password format is incorrect.", "Password");
        }
    }

    private void checkUsernameFormat(String username) throws InvalidFormatException {
        if (!username.matches("^[^\\s]+$")) {
            throw new InvalidFormatException("Username format is incorrect.", "Username");
        }
    }

    private void checkUsernameAvailability(String username) throws InvalidAuthenticationException {
        if (userRepository.getUserByName(username) != null) {
            throw new InvalidAuthenticationException("Username is already taken.","Username");
        }
    }

    private User createNewUser(Account account) {
        return account.makeUser();
    }

    private void checkUsernameAndPassword(String username, String password) throws InvalidAuthenticationException, PasswordIsWrongException {
        if (userRepository.getUserByName(username) == null) {
            throw new InvalidAuthenticationException("Username is invalid.","Username");
        }
        if (!userRepository.getUserByName(username).getPassword().equals(password)) {
            throw new PasswordIsWrongException("Password is wrong");
        }
    }

    private void registerCustomer(Account account, String token) {
        userRepository.save(createNewUser(account));
    }

    private void registerSeller(Account account, String token) {
        userRepository.save(createNewUser(account));
    }

    private void registerAdmin(Account account, String token) throws NoAccessException, InvalidTokenException {
        Session userSession = Session.getSession(token);
        if (userSession.getLoggedInUser() == null) {
            if (userRepository.doWeHaveAManager()) {
                throw new NoAccessException("You are not allowed to do that.");
            } else {
                userRepository.save(account.makeUser());
            }
        } else {
            if (userSession.getLoggedInUser().getRole() != Role.ADMIN) {
                throw new NoAccessException("You are not allowed to do that.");
            } else {
                userRepository.save(account.makeUser());
            }
        }
    }

}
