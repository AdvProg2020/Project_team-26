package view;

import view.main.MainPageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ViewManager {
    private boolean isUserLoggedIn;
    private List<View> pathOfView;
    private List<String> helpFormatForPrint;
    public Scanner scan;

    public ViewManager() {
        pathOfView = new ArrayList<>();
        helpFormatForPrint = new ArrayList<>();
        isUserLoggedIn = false;
        scan = new Scanner(System.in);
    }

    public void startProgram(){
        MainPageView startView = new MainPageView();
        startView.run(this);
    }

    public void setUserLoggedIn(boolean userLoggedIn) {
        isUserLoggedIn = userLoggedIn;
    }

    public boolean getIsUserLoggedin() {
        return isUserLoggedIn;
    }

    public List<String> getHelpFormatForPrint() {
        return helpFormatForPrint;
    }

    public List<View> getPathOfView() {
        return pathOfView;
    }

    public void setHelpFormatForPrint(List<String> helpFormatForPrint) {
        this.helpFormatForPrint = helpFormatForPrint;
    }
    public void showResult(/*//*/){

    }
    //public void setTheCommandsForUserDependentOnSituation()
}
