package view;

import exception.AlreadyLoggedInException;
import model.Session;
import model.repository.RepositoryContainer;
import model.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MainPageViewTest {
    ViewManager manager;
    view.main.MainPageView mainPageView;

    @BeforeEach
    void setUp() {
        manager = new ViewManager();
        mainPageView = new view.main.MainPageView(manager);
        Session.initializeFake((UserRepository) (new RepositoryContainer()).getRepository("UserRepository"));
    }
    @Test
    void run() {
        setUp();
        InputOutput.input.add("offs");
        InputOutput.input.add("back");
        InputOutput.input.add("back");
        mainPageView.run();
        InputOutput.input.add("products");
        InputOutput.input.add("back");
        InputOutput.input.add("back");
        mainPageView.run();
        InputOutput.input = new ArrayList<>();
        InputOutput.inputNumber = 0;
        InputOutput.input.add("wergbv");
        InputOutput.input.add("back");
        InputOutput.output = new ArrayList<>();
        mainPageView.run();
        Assertions.assertEquals("invalid command pattern",InputOutput.getOutput().get(0));
    }
    @Test
    void authorizing() {
        setUp();
        manager.setToken("notloggedin");//
        InputOutput.input.add("login test6");
        InputOutput.input.add("password6");
        InputOutput.input.add("back");
        mainPageView.run();
        Assertions.assertEquals(true,manager.getIsUserLoggedIn());
        manager.setUserLoggedIn(false);
        InputOutput.input.add("create account seller hi");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("hello");
        InputOutput.input.add("back");
        manager.setToken("notloggedin");
        mainPageView.run();
        InputOutput.input.add("login hi");
        InputOutput.input.add("hello");
        InputOutput.input.add("back");
        InputOutput.input.add("back");
        manager.setToken("admin");
        mainPageView.run();
        Assertions.assertEquals(true, manager.getIsUserLoggedIn());
    }

    @Test
    void helpTest() {
        InputOutput.input.add("help");
        InputOutput.input.add("back");
        InputOutput.input.add("help");
        InputOutput.input.add("back");
        mainPageView.run();
        List<String> commandList = new ArrayList<>();
        commandList.add("help");
        commandList.add("offs");
        commandList.add("products");
        commandList.add("login [username]");
        commandList.add("create account [manager|buyer|seller] [username]");
        Assertions.assertEquals(commandList,InputOutput.getOutput());
        InputOutput.output = new ArrayList<>();
        manager.setUserLoggedIn(true);
        mainPageView.run();
        commandList.remove(3);
        commandList.remove(3);
        commandList.add("logout");
        Assertions.assertEquals(commandList,InputOutput.getOutput());
    }
    @Test
    void logout() {
        setUp();
        manager.setUserLoggedIn(true);
        mainPageView.logout("notloggedin");
        Assertions.assertEquals("You are not logged in.",InputOutput.getOutput().get(0));
        manager.setUserLoggedIn(true);
        mainPageView.logout("admin");
        Assertions.assertEquals(false,manager.getIsUserLoggedIn());
        InputOutput.input.add("jhihs");
        InputOutput.input.add("back");
        mainPageView.run();
        Assertions.assertEquals("invalid command pattern",InputOutput.getOutput().get(1));
        manager.setUserLoggedIn(true);
        InputOutput.input.add("logout");
        InputOutput.input.add("back");
        manager.setToken("admin");
        mainPageView.run();
        Assertions.assertEquals(false,manager.getIsUserLoggedIn());
    }
}
