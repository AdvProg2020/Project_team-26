package view.main;

import model.Session;
import repository.RepositoryContainer;
import repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.terminal.ControllerContainer;
import view.terminal.InputOutput;
import view.terminal.ViewManager;
import view.terminal.main.AuthenticationValidCommands;
import view.terminal.main.AuthenticationView;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthenticationViewTest {
    ViewManager manager;
    AuthenticationView authenticationView;

    @BeforeEach
    void setUp() {
        RepositoryContainer repositoryContainer = new RepositoryContainer();
        ControllerContainer controllerContainer = new ControllerContainer(repositoryContainer);
        manager = new ViewManager(controllerContainer);
        authenticationView = new AuthenticationView(manager, "hello");
        Session.initializeFake((UserRepository) (new RepositoryContainer()).getRepository("UserRepository"));
    }

    @Test
    void login() {
        setUp();
        InputOutput.input.add("password6");
        manager.setToken("admin");
        authenticationView.setInput("login test6");
        authenticationView.run();
        Assertions.assertEquals(true, manager.getIsUserLoggedIn());
        manager.setUserLoggedIn(false);
        InputOutput.input.add("chert");
        authenticationView.login(Pattern.compile(AuthenticationValidCommands.LoginAccount.toString()).matcher("login back"));
        Assertions.assertEquals(false, manager.getIsUserLoggedIn());
        manager.setUserLoggedIn(false);
        InputOutput.input.add("password6");
        InputOutput.input.add("back");
        authenticationView.login(Pattern.compile(AuthenticationValidCommands.LoginAccount.toString()).matcher("login test6"));
        Assertions.assertEquals(true, manager.getIsUserLoggedIn());
        //todo
    }

    @Test
    void register() {
        setUp();
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("amir@yahoo.com");
        manager.setToken("notloggedin");
        authenticationView.register(Pattern.compile(AuthenticationValidCommands.CreateAccount.toString()).matcher("create account seller hi"));
        assertEquals(InputOutput.now, "registered");
        manager.setUserLoggedIn(false);
        InputOutput.input.add("hello");
        authenticationView.login(Pattern.compile(AuthenticationValidCommands.LoginAccount.toString()).matcher("login hi"));
        Assertions.assertEquals(true, manager.getIsUserLoggedIn());
        assertEquals(InputOutput.now, "logged in");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("amir@yahoo.com");
        InputOutput.input.add("45");
        manager.setToken("admin");
        authenticationView.register(Pattern.compile(AuthenticationValidCommands.CreateAccount.toString()).matcher("create account manager 4 5"));
        assertEquals(InputOutput.now, "registered");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("amir@yahoo.com");
        manager.setToken("admin");
        authenticationView.setInput("create account manager 67");
        authenticationView.run();
        assertEquals(InputOutput.now, "registered");
        InputOutput.input.add("he  llo");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("amir@yahoo.com");
        manager.setToken("seller");
        authenticationView.register(Pattern.compile(AuthenticationValidCommands.CreateAccount.toString()).matcher("create account buyer 89"));
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("amir@yahoo.com");
        manager.setToken("seller");
        authenticationView.setInput("create account buyer 90");
        authenticationView.run();
        assertEquals("registered", InputOutput.now);
        InputOutput.input.add("89");
        InputOutput.input.add("hel lo");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("amir@yahoo.com");
        manager.setToken("seller");
        authenticationView.setInput("create account buyer 8 9");
        authenticationView.run();
        assertEquals(InputOutput.now, "registered");
    }


}
