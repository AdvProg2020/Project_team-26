package client.gui;

public class Constants {

    public static final Manager manager = new Manager();
    public static final int productGridColumnCount = 5;
    public static  String registerAddress = Constants.manager.getHostPort() + "/register";
    public static  String loginAddress = Constants.manager.getHostPort() + "/login";
    public static  String getUserByNameAddress = Constants.manager.getHostPort() + "/getUserByName";
    public static  String getUserByIdAddress = Constants.manager.getHostPort() + "/getUserById";
    public static  String getManagersAddress = Constants.manager.getHostPort() + "/getManagers/";
    public static  String changePasswordAddress = Constants.manager.getHostPort() + "/getManagers/";
    public static  String changePasswordWithOldAddress = Constants.manager.getHostPort() + "/getManagers/";
    public static  String changeImageِAddress = Constants.manager.getHostPort() + "/getManagers/";
    public static  String deleteUserAddress = Constants.manager.getHostPort() + "/getManagers/";
    public static  String getUserByTokenAddress = Constants.manager.getHostPort() + "/getUserByToken";
    public static  String changeInfoAddress = Constants.manager.getHostPort() + "/getUserByToken";
}
