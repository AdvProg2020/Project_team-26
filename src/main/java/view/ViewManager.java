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

    public ViewManager() {
        pathOfView = new ArrayList<>();
        helpFormatForPrint = new ArrayList<>();
        isUserLoggedIn = true;
        scan = new Scanner(System.in);
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

    public void setPathOfView(View view) {
        this.pathOfView.add(view);
    }

    public void revomeFromPath(View view) {

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

    public List<String> getHelpFormatForPrint() {
        return helpFormatForPrint;
    }

    public List<View> getPathOfView() {
        return pathOfView;
    }

    public void setHelpFormatForPrint(List<String> helpFormatForPrint) {
        this.helpFormatForPrint = helpFormatForPrint;
    }

    public void showResult(/*//*/) {

    }
    public void printError() {


    }
    //public void setTheCommandsForUserDependentOnSituation()
}
