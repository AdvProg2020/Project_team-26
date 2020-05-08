package controller.account;

import controller.interfaces.account.IAuthenticationController;
import exception.*;
import model.Role;
import model.Session;
import model.User;
import model.repository.RepositoryContainer;
import model.repository.UserRepository;

public class AuthenticationController implements IAuthenticationController {

    UserRepository userRepository;

    public AuthenticationController(RepositoryContainer repositoryContainer) {
        this.userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
    }

    public void login(String username, String password, String token) throws Exceptions.InvalidUserNameException, Exceptions.InvalidPasswordException, Exceptions.IncorrectPasswordFormat, Exceptions.IncorrectUsernameFormat, Exceptions.UsernameAlreadyExists, PasswordIsWrongException, Exceptions.UserNameDoesntExist, InvalidTokenException {
        Session userSession = Session.getSession(token);
        checkPasswordFormat(password);
        checkUsernameFormat(username);
        checkUsernameAndPassword(username, password);
        userSession.login(userRepository.getUserByName(username));
    }

    public void register(Account account, String token) throws InvalidFormatException, Exceptions.InvalidAccessDemand, Exceptions.IncorrectPasswordFormat, Exceptions.IncorrectUsernameFormat, Exceptions.UsernameAlreadyExists, NoAccessException, InvalidTokenException {
        Session userSession = Session.getSession(token);
        checkPasswordFormat(account.getPassword());
        checkUsernameFormat(account.getUsername());
        checkUsernameAvailability(account.getUsername());
        if (userSession.getLoggedInUser() != null) {
            registerAdmin(account,token);
        } else {
            switch (account.getRole()) {
                case CUSTOMER: registerCustomer(account,token);
                break;
                case SELLER: registerSeller(account,token);
                break;
                case ADMIN: registerAdmin(account,token);
            }
        }
    }

    public void logout(String token) throws NotLoggedINException, InvalidTokenException {
        Session userSession = Session.getSession(token);
        if (userSession.getLoggedInUser() == null) {
            throw new Exceptions.UnSuccessfulLogout("You are not logged in.");
        } else {
            userSession.logout();
        }
    }

    private void checkPasswordFormat(String password) throws Exceptions.IncorrectPasswordFormat {
        if (!password.matches("^[^\\s]+$")) {
            throw new Exceptions.IncorrectPasswordFormat("Password format is incorrect.");
        }
    }

    private void checkUsernameFormat(String username) throws Exceptions.IncorrectUsernameFormat {
        if (!username.matches("^[^\\s]+$")) {
            throw new Exceptions.IncorrectUsernameFormat("Username format is incorrect.");
        }
    }

    private void checkUsernameAvailability(String username) throws Exceptions.UsernameAlreadyExists {
        if (userRepository.getUserByName(username) != null) {
            throw new Exceptions.UsernameAlreadyExists("Username is already taken.");
        }
    }

    private User createNewUser(Account account) {
        return new User(account);
    }

    private void checkUsernameAndPassword(String username, String password) throws Exceptions.UserNameDoesntExist, PasswordIsWrongException {
        if (userRepository.getUserByName(username) == null) {
            throw new Exceptions.UserNameDoesntExist("Username is invalid.");
        }
        if (!userRepository.getUserByName(username).getPassword().equals(password)) {
            throw new PasswordIsWrongException("Password is wrong");
        }
    }

    private void registerCustomer(Account account,String token) {
        userRepository.save(createNewUser(account));
    }

    private void registerSeller(Account account,String token) {
        userRepository.save(createNewUser(account));
    }

    private void registerAdmin(Account account,String token) throws NoAccessException {
        Session userSession = Session.getSession(token);
        if(userSession.getLoggedInUser() == null) {
            if(userRepository.doWeHaveAManager()) {
                throw new NoAccessException("You are not allowed to do that.");
            } else {
                userRepository.save(createNewUser(account));
            }
        } else {
            if(userSession.getLoggedInUser().getRole() != Role.ADMIN) {
                throw new NoAccessException("You are not allowed to do that.");
            } else {
                userRepository.save(createNewUser(account));
            }
        }
    }

}
