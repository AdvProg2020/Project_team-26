package client.gui;

public class Constants {

    public static  Manager manager = new Manager();
    public static  int productGridColumnCount = 5;
    public static  String registerAddress = Constants.manager.getHostPort() + "/register";
    public static  String loginAddress = Constants.manager.getHostPort() + "/login";
    public static  String getUserByNameAddress = Constants.manager.getHostPort() + "/getUserByName";
    public static  String getUserByIdAddress = Constants.manager.getHostPort() + "/getUserById";
    public static  String getManagersAddress = Constants.manager.getHostPort() + "/getManagers/";
}
