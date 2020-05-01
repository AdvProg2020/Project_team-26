package controller.account;

import controller.Exceptions;
import controller.interfaces.account.IAuthenticationController;
import exception.NoAccessException;
import exception.PasswordIsWrong;
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

    public void login(String username, String password, String token) throws Exceptions.InvalidUserNameException, Exceptions.InvalidPasswordException, Exceptions.IncorrectPasswordFormat, Exceptions.IncorrectUsernameFormat, Exceptions.UsernameAlreadyExists, PasswordIsWrong, Exceptions.UserNameDoesntExist {
        Session userSession = Session.getSession(token);
        checkPasswordFormat(password);
        checkUsernameFormat(username);
        checkUsernameAndPassword(username, password);
        userSession.login(userRepository.getUserByName(username));
    }

    public void register(Account account, String token) throws Exceptions.InvalidUserNameException, Exceptions.InvalidAccessDemand, Exceptions.IncorrectPasswordFormat, Exceptions.IncorrectUsernameFormat, Exceptions.UsernameAlreadyExists, NoAccessException {
        Session userSession = Session.getSession(token);
        checkPasswordFormat(account.getPassword());
        checkUsernameFormat(account.getUsername());
        checkUsernameAvailability(account.getUsername());
        if (userSession.getLoggedInUser() != null) {
            addAManager(account, userSession);
        } else {
            addANormalUser(account, userSession);
        }
    }

    public void logout(String token) throws Exceptions.UnSuccessfulLogout {
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

    private void addAManager(Account account, Session userSession) throws Exceptions.InvalidAccessDemand, NoAccessException {
        if (userSession.getLoggedInUser().getRole() != Role.ADMIN) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            userRepository.save(createNewUser(account));
        }
    }

    private void addANormalUser(Account account, Session userSession) throws Exceptions.InvalidAccessDemand, NoAccessException {
        if (account.getRole() == Role.ADMIN) {
            if (userRepository.doWeHaveAManager()) {
                throw new NoAccessException("You are not allowed to do that.");
            } else {
                userRepository.save(createNewUser(account));
            }
        } else {
            userRepository.save(createNewUser(account));
        }
    }

    private User createNewUser(Account account) {
        return new User(account);
    }

    private void checkUsernameAndPassword(String username, String password) throws Exceptions.UserNameDoesntExist, PasswordIsWrong {
        if (userRepository.getUserByName(username) == null) {
            throw new Exceptions.UserNameDoesntExist("Username is invalid.");
        }
        if (!userRepository.getUserByName(username).getPassword().equals(password)) {
            throw new PasswordIsWrong("Password is wrong");
        }
    }

}
