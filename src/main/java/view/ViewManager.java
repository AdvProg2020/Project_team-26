package view;

import controller.interfaces.product.IProductController;
import exception.AlreadyLoggedInException;
import exception.InvalidIdException;
import model.Product;
import view.main.AuthenticationView;
import view.main.MainPageView;
import view.products.single.SingleProductView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewManager {
    private boolean isUserLoggedIn;
    private String token;
    public Scanner scan;
    public IO inputOutput;
    private ControllerContainer controllerContainer;

    public ViewManager() {
        isUserLoggedIn = true;
        scan = new Scanner(System.in);
        isUserLoggedIn = false;
        inputOutput = new InputOutput();
        controllerContainer = new ControllerContainer();
    }

    public ControllerContainer getControllerContainer() {
        return controllerContainer;
    }


    public String getToken() {
        return token;
    }


    public void setTokenFromController(String error) {
        this.inputOutput.println(error);
        this.token = token;
    }

    public void setLoggedINByController() {
        //  this.isUserLoggedIn
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void startProgram() {
        MainPageView startView = new MainPageView(this);
        startView.run();
    }

    public void setUserLoggedIn(boolean userLoggedIn) {
        isUserLoggedIn = userLoggedIn;
    }

    public boolean getIsUserLoggedIn() {
        return isUserLoggedIn;
    }

    public void printError() {


    }

    public boolean checkTheInputIsInteger(String input) {
        Matcher matcher = Pattern.compile("^[0-9]*$").matcher(input);
        if (matcher.find())
            return true;
        return false;
    }

    public void singleProductView(Matcher matcher) {
        IProductController productController = (IProductController) controllerContainer.getController("ProductController");
        matcher.find();
        String id = matcher.group(1);
        if (this.checkTheInputIsInteger(id)) {
            int productId = Integer.parseInt(id);
            try {
                Product product = productController.getProductById(productId, this.getToken());
                SingleProductView singleProductView = new SingleProductView(this, product);
                singleProductView.run();
            } catch (InvalidIdException e) {
                this.inputOutput.println(e.getMessage());
            }
            return;
        }
        this.inputOutput.println("the id is invalid format.");
    }

    public boolean loginInAllPagesEssential() {
        while (!this.getIsUserLoggedIn()) {
            this.inputOutput.println("first login.\nenter your username or back.");
            String username = this.inputOutput.nextLine();
            if (username.equalsIgnoreCase("back"))
                return false;
            AuthenticationView authenticationView = new AuthenticationView(this, "login " + username);
            authenticationView.login(Pattern.compile("login (.*)").matcher("login " + username));
        }
        return true;
    }

    public void loginInAllPagesOptional(String command) {
        if (this.getIsUserLoggedIn()) {
            this.inputOutput.println("you are already logged in");
            return;
        }
        AuthenticationView authenticationView = new AuthenticationView(this, command);
        authenticationView.login(Pattern.compile("login (.*)").matcher(command));
        return;
    }

    public void registerInAllPagesOptional(String command) {
        if (this.getIsUserLoggedIn()) {
            this.inputOutput.println("you are already logged in first logout");
            return;
        }
        AuthenticationView authenticationView = new AuthenticationView(this, command);
        authenticationView.register(Pattern.compile("register (.*)").matcher(command));
        return;
    }

    public void logoutInAllPages() {
        if (!this.getIsUserLoggedIn()) {
            this.inputOutput.println("you should first login");
            return;
        }
        new MainPageView(this).logout(this.getToken());
    }
    //public void setTheCommandsForUserDependentOnSituation()
}
