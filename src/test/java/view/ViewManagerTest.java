package view;

import model.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.RepositoryContainer;
import repository.UserRepository;
import view.main.MainPageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ViewManagerTest {
    ViewManager manager;
    MainPageView mainPageView;

    @BeforeEach
    void setUp() {
        manager = new ViewManager();
        mainPageView = new view.main.MainPageView(manager);
        Session.initializeFake((UserRepository) (new RepositoryContainer()).getRepository("UserRepository"));
    }

    @Test
    void createDate() {
        Date test;
        InputOutput.input.add("2002");
        InputOutput.input.add("4");
        InputOutput.input.add("5");
        InputOutput.input.add("13");
        InputOutput.input.add("23");
        Date date = manager.createDate();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        try {
            test = formatter.parse("5-4-2002 13:23:00");
            Assertions.assertEquals(date, test);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        InputOutput.input.add("gjy");
        InputOutput.input.add("2002");
        InputOutput.input.add("14");
        InputOutput.input.add("4");
        InputOutput.input.add("uytregh");
        InputOutput.input.add("67");
        InputOutput.input.add("5");
        InputOutput.input.add("27");
        InputOutput.input.add("13");
        InputOutput.input.add("98");
        InputOutput.input.add("23");
        date = manager.createDate();
        formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        try {
            test = formatter.parse("5-4-2002 13:23:00");
            Assertions.assertEquals(date, test);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    void checkNumber() {
        Assertions.assertEquals(manager.checkTheInputIsIntegerOrLong("098765432", true), true);
        Assertions.assertEquals(manager.checkTheInputIsIntegerOrLong("098y765432", true), false);
        Assertions.assertEquals(manager.checkTheInputIsIntegerOrLong("098765.432", true), false);
        Assertions.assertEquals(manager.checkTheInputIsIntegerOrLong("-098765432", true), false);
        Assertions.assertEquals(manager.checkTheInputIsDouble("098765432"), true);
        Assertions.assertEquals(manager.checkTheInputIsDouble("-098765432"), false);
        Assertions.assertEquals(manager.checkTheInputIsDouble("8934.839"), true);
    }

    @Test
    void abstractTest() {
        mainPageView.setInput("hi");
        Assertions.assertEquals(mainPageView.getInput(), "hi");
        Assertions.assertEquals(mainPageView.getManager(), manager);
    }

    @Test
    void logOut() {
        manager.setUserLoggedIn(true);
        manager.setToken("admin");
        manager.logoutInAllPages();
        Assertions.assertEquals(false, manager.getIsUserLoggedIn());
        manager.setUserLoggedIn(false);
        manager.setToken("admin");
        manager.logoutInAllPages();
        Assertions.assertEquals(false, manager.getIsUserLoggedIn());
    }

    @Test
    void register() {
        manager.setUserLoggedIn(true);
        manager.setToken("admin");
        manager.logoutInAllPages();
        Assertions.assertEquals(false, manager.getIsUserLoggedIn());
        manager.setUserLoggedIn(false);
        manager.setToken("admin");
        manager.logoutInAllPages();
        Assertions.assertEquals(false, manager.getIsUserLoggedIn());
    }

    @Test
    void login() {
        manager.setUserLoggedIn(true);
        manager.setToken("admin");
        manager.logoutInAllPages();
        Assertions.assertEquals(false, manager.getIsUserLoggedIn());
        manager.setUserLoggedIn(false);
        manager.setToken("admin");
        manager.logoutInAllPages();
        Assertions.assertEquals(false, manager.getIsUserLoggedIn());
    }

}
