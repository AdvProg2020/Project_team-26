package client.gui;

public class Constants {

    public static final Manager manager = new Manager();
    public static final int productGridColumnCount = 5;
    public static final String registerAddress = Constants.manager.getHostPort() + "/register";
    public static final String loginAddress = Constants.manager.getHostPort() + "/login";
    public static final String getUserByNameAddress = Constants.manager.getHostPort() + "/getUserByName";
}
