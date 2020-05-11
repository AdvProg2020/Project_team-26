package view;

import exception.AlreadyLoggedInException;
import view.main.MainPageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewManager {
    private boolean isUserLoggedIn;
    private String session;
    private String token;
    private List<View> pathOfView;
    private List<String> helpFormatForPrint;
    public Scanner scan;
    public IO inputOutput;
    private ControllerContainer controllerContainer;

    public ViewManager() {
        pathOfView = new ArrayList<>();
        helpFormatForPrint = new ArrayList<>();
        isUserLoggedIn = true;
        scan = new Scanner(System.in);
        isUserLoggedIn = false;
        inputOutput = new InputOutput();
        controllerContainer = new ControllerContainer();
    }

    public ControllerContainer getControllerContainer() {
        return controllerContainer;
    }

    public String getSession() {
        return session;
    }

    public String getToken() {
        return token;
    }

    public void setSession(String session) {
        this.session = session;
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

    public void showList(List<String> list) {
        for (String s : list) {
            inputOutput.println(s);
        }

    }
    public boolean checkTheInputIsInteger(String input) {
        Matcher matcher = Pattern.compile("^[0-9]*$").matcher(input);
        if (matcher.find())
            return true;
        return false;
    }
    //public void setTheCommandsForUserDependentOnSituation()
}
