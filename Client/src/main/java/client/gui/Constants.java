package client.gui;

public class Constants {

    public static final Manager manager = new Manager();
    public static final int productGridColumnCount = 5;
    public static String registerAddress = Constants.manager.getHostPort() + "/register";
    public static String loginAddress = Constants.manager.getHostPort() + "/login";
    public static String getUserByNameAddress = Constants.manager.getHostPort() + "/getUserByName";
    public static String getUserByIdAddress = Constants.manager.getHostPort() + "/getUserById";
    public static String getManagersAddress = Constants.manager.getHostPort() + "/getManagers/";
    public static String changePasswordAddress = Constants.manager.getHostPort() + "/getManagers/";
    public static String changePasswordWithOldAddress = Constants.manager.getHostPort() + "/getManagers/";
    public static String changeImageِAddress = Constants.manager.getHostPort() + "/getManagers/";
    public static String deleteUserAddress = Constants.manager.getHostPort() + "/getManagers/";
    public static String getUserByTokenAddress = Constants.manager.getHostPort() + "/getUserByToken";
    public static String changeInfoAddress = Constants.manager.getHostPort() + "/getUserByToken";
    public static String changeInfoByMapAddress = Constants.manager.getHostPort() + "/getUserByToken";
    public static String doWeHaveManagerAddress = Constants.manager.getHostPort() + "/controller/method/do-we-have-a-manager";
    public static String logoutAddress = Constants.manager.getHostPort() + "/controller/method/do-we-have-a-manager";
    public static String getAllUsersAddress = Constants.manager.getHostPort() + "/controller/method/do-we-have-a-manager";
    public static String getUserInfoAddress = Constants.manager.getHostPort() + "/controller/method/do-we-have-a-manager";
    public static String changeBalanceAddress = Constants.manager.getHostPort() + "/controller/method/do-we-have-a-manager";
    public static String getCompanyNameAddress = Constants.manager.getHostPort() + "/getUserByToken";
    public static String getBalanceAddress = Constants.manager.getHostPort() + "/controller/method/do-we-have-a-manager";
    public static String getRoleAddress = Constants.manager.getHostPort() + "/controller/method/do-we-have-a-manager";
    public static String getOrdersWithFilterAddress;
    public static String getOrdersAddress;
    public static String getOrderHistoryForSellerAddress;
    public static String getASingleOrderAddress;
}
