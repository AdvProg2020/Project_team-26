package view;

import exception.AlreadyLoggedInException;
import model.Session;
import model.repository.RepositoryContainer;
import model.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.main.AuthenticationValidCommands;
import view.main.AuthenticationView;

import java.util.regex.Pattern;

public class AuthenticationViewTest {
    ViewManager manager;
    AuthenticationView authenticationView;
    @BeforeEach
    void setUp(){
        manager = new ViewManager();
        authenticationView = new AuthenticationView(manager,"hello");
        Session.initializeFake((UserRepository) (new RepositoryContainer()).getRepository("UserRepository"));
    }
    @Test
    void login() throws AlreadyLoggedInException {
        setUp();
        InputOutput.input.add("password6");
        manager.setToken("admin");
        authenticationView.login(Pattern.compile(AuthenticationValidCommands.LoginAccount.toString()).matcher("login test6"));
        Assertions.assertEquals(true,manager.getIsUserLoggedin());
        manager.setUserLoggedIn(false);
        InputOutput.input.add("chert");
        authenticationView.login(Pattern.compile(AuthenticationValidCommands.LoginAccount.toString()).matcher("login back"));
        Assertions.assertEquals(false,manager.getIsUserLoggedin());
        manager.setUserLoggedIn(false);
        InputOutput.input.add("password6");
        InputOutput.input.add("back");
        authenticationView.login(Pattern.compile(AuthenticationValidCommands.LoginAccount.toString()).matcher("login test6"));
        Assertions.assertEquals(true,manager.getIsUserLoggedin());
        //todo
    }
    @Test
    void register() throws AlreadyLoggedInException {
        setUp();
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        manager.setToken("admin");
        authenticationView.register(Pattern.compile(AuthenticationValidCommands.CreateAccount.toString()).matcher("create account seller hi"));
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        authenticationView.register(Pattern.compile(AuthenticationValidCommands.CreateAccount.toString()).matcher("create account manager 45"));
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        manager.setToken("seller");
        authenticationView.register(Pattern.compile(AuthenticationValidCommands.CreateAccount.toString()).matcher("create account buyer 89"));







    }


}
