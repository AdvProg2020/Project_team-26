package view;

import view.main.MainPageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ViewManager {
    private boolean isUserLoggedIn;
    private String session;
    private String tocken;
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

    public String getTocken() {
        return tocken;
    }

    public void setSession(String session) {
        this.session = session;
    }



    public void setTocken(String tocken) {
        this.tocken = tocken;
    }

    public void startProgram() {
        MainPageView startView = new MainPageView(this);
        startView.run();
    }

    public void setUserLoggedIn(boolean userLoggedIn) {
        isUserLoggedIn = userLoggedIn;
    }

    public boolean getIsUserLoggedin() {
        return isUserLoggedIn;
    }

    public void printError() {


    }

    public void showList(List<String> list) {
        for (String s : list) {
            inputOutput.println(s);
        }

    }
    //public void setTheCommandsForUserDependentOnSituation()
}
