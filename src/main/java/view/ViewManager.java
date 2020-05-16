package view;

import controller.interfaces.product.IProductController;
import controller.interfaces.session.ISessionController;
import exception.InvalidIdException;
import model.Product;
import view.main.AuthenticationView;
import view.main.MainPageView;
import view.products.single.SingleProductView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewManager {
    private boolean isUserLoggedIn;
    private String token;
    public IO inputOutput;
    private ISessionController sessionController;
    private ControllerContainer controllerContainer;

    public ViewManager() {
        isUserLoggedIn = false;
        inputOutput = new InputOutput();
        controllerContainer = new ControllerContainer();
        sessionController = (ISessionController) controllerContainer.getController(ControllerContainer.Controller.SessionController);
    }

    public Object getController(ControllerContainer.Controller controller) {
        return controllerContainer.getController(controller);
    }


    public String getToken() {
        return token;
    }


    public void setTokenFromController(String error) {
        this.inputOutput.println(error);
        inputOutput.println("if you want dont want to set token type no then program will be finished.");
        if (inputOutput.nextLine().equals("no"))
            System.exit(0);
        setToken(sessionController.createToken());
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

    public boolean checkTheInputIsInteger(String input) {
        Matcher matcher = Pattern.compile("^\\d+$").matcher(input);
        if (matcher.find())
            return true;
        return false;
    }

    public boolean checkTheInputIsDouble(String input) {
        Matcher matcher = Pattern.compile("^\\d+\\.\\d+").matcher(input);
        if (matcher.find())
            return true;
        return false;
    }

    public void singleProductView(Matcher matcher) {
        IProductController productController = (IProductController) controllerContainer.getController(ControllerContainer.Controller.ProductController);
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

    public void loginInAllPagesEssential() {
        while (!this.getIsUserLoggedIn()) {
            this.inputOutput.println("enter your username or back.");
            String username = this.inputOutput.nextLine();
            if (username.equalsIgnoreCase("back"))
                return;
            AuthenticationView authenticationView = new AuthenticationView(this, "login " + username);
            authenticationView.login(Pattern.compile("login (.*)").matcher("login " + username));
        }
        return;
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

    public Date createDate() {
        String year = getYear();
        String month = getValidTimeAndDate("month", 12, 1);
        String day = getValidTimeAndDate("day", 31, 1);
        String hour = getValidTimeAndDate("hour", 23, 0);
        String minute = getValidTimeAndDate("minute", 59, 0);
        String date = day + "-" + month + "-" + year + " " + hour + ":" + minute + ":" + "00";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private String getYear() {
        inputOutput.println("enter year");
        String year;
        while (true) {
            year = inputOutput.nextLine();
            if (checkTheInputIsInteger(year))
                return year;
            inputOutput.println("please enter integer.");
        }
    }

    private String getValidTimeAndDate(String field, int max, int min) {
        inputOutput.println("enter " + field);
        String valid;
        while (true) {
            valid = inputOutput.nextLine();
            if (checkTheInputIsInteger(valid)) {
                if (Long.parseLong(valid) <= max && Long.parseLong(valid) >= min)
                    return valid;
                else
                    inputOutput.println("please enter " + field + " between " + min + " and " + max);
            } else
                inputOutput.println("please enter integer.");
        }
    }
}
