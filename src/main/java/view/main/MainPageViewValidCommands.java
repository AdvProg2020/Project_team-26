package view.main;

import view.View;
import view.ViewManager;
import view.offs.AllOffsViewI;
import view.products.all.AllProductViewI;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainPageViewValidCommands {
    Back("back", null) {
        @Override
        public void goToFunction(MainPageView page) {
            page.back();
        }
    },
    Exit("exit", null) {
        @Override
        public void goToFunction(MainPageView page) {

        }
    },
    ShowProducts("products", new AllProductViewI(MainPageViewValidCommands.manager)) {
        @Override
        public void goToFunction(MainPageView page) {

        }
    },
    ShowOffs("offs", new AllOffsViewI(MainPageViewValidCommands.manager)) {
        @Override
        public void goToFunction(MainPageView page) {

        }
    },
    Help("help", null) {
        @Override
        public void goToFunction(MainPageView page) {
            page.help(page.getManager().getIsUserLoggedin());
        }
    },
    Logout("logout", null) {
        @Override
        public void goToFunction(MainPageView page) {
            if (page.getManager().getIsUserLoggedin())
                page.logout(manager.getTocken());
            page.printError();
        }
    },
    CreateAccount("create\\s+account\\s+(buyer|seller|manager)\\s+(.*)", null) {
        @Override
        public void goToFunction(MainPageView page) {
            if (!page.getManager().getIsUserLoggedin())
                page.authorizing();
            page.printError();
        }
    },
    LoginAccount("login\\s+(.*)", null) {
        @Override
        public void goToFunction(MainPageView page) {
            if (!page.getManager().getIsUserLoggedin())
                page.authorizing();
            page.printError();
        }
    };

    private Pattern commandPattern;
    private View view = null;
    private MainPageView function = null;
    public static ViewManager manager;

    public void setManager(ViewManager manager) {
        MainPageViewValidCommands.manager = manager;
    }

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    public View getView() {
        return this.view;
    }

    public abstract void goToFunction(MainPageView page);

    MainPageViewValidCommands(String output, View view) {
        this.commandPattern = Pattern.compile(output);
        this.view = view;
    }

    List<String> getvalidCommands() {
        List<String> commandList = new ArrayList<>();
        /**
         *
         *
         *
         */
        return commandList;
    }
}
