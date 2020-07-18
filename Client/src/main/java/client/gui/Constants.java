package client.gui;

import java.net.URI;

public class Constants {

    public static final Manager manager = new Manager();
    public static final int productGridColumnCount = 5;

    /**
     * AuthenticationController Methods.
     */

    public static String getAuthenticationControllerLoginAddress() {
        return manager.getHostPort() + "/controller/method/login";
    }

    public static String getAuthenticationControllerRegister() {
        return manager.getHostPort() + "/controller/method/register";
    }

    public static String getAuthenticationControllerLogoutAddress() {
        return manager.getHostPort() + "/controller/method/logout";
    }

    public static String getAuthenticationControllerDoWeHaveAManagerAddress() {
        return manager.getHostPort() + "/controller/method/do-we-have-a-manager";
    }

    /**
     * ShowUserController Methods.
     */

    public static String getShowUserControllerGetUsersAddress() {
        return manager.getHostPort() + "/controller/method/get-users";
    }

    public static String getShowUserControllerGetUserByNameAddress() {
        return manager.getHostPort() + "/controller/method/get-user-by-name";
    }

    public static String getShowUserControllerGetUserByIdAddress() {
        return manager.getHostPort() + "/controller/method/get-user-by-id";
    }

    public static String getShowUserControllerGetUserInfoAddress() {
        return manager.getHostPort() + "/controller/method/get-user-info";
    }

    public static String getShowUserControllerGetManagersAddress() {
        return manager.getHostPort() + "/controller/method/get-managers";
    }

    public static String getShowUserControllerDeleteAddress() {
        return manager.getHostPort() + "/controller/method/user/delete";
    }

    public static String getShowUserControllerGetUserByTokenAddress() {
        return manager.getHostPort() + "/controller/method/get-user-by-token";
    }

    /**
     * UserInfoController Methods.
     */

    public static String getUserInfoControllerChangePasswordWithOldPasswordAddress() {
        return manager.getHostPort() + "/controller/method/user/change-password-old-way";
    }

    public static String getUserInfoControllerChangePasswordAddress() {
        return manager.getHostPort() + "/controller/method/change-password";
    }

    public static String getUserInfoControllerChangeImageAddress() {
        return manager.getHostPort() + "/controller/method/change-image";
    }

    public static String getUserInfoControllerChangeInfoAddress() {
        return manager.getHostPort() + "/controller/method/change-info";
    }

    public static String getUserInfoControllerChangeBalanceAddress() {
        return manager.getHostPort() + "/controller/method/change-balance";
    }

    public static String getUserInfoControllerGetCompanyNameAddress() {
        return manager.getHostPort() + "/controller/method/user/get-company-name";
    }

    public static String getUserInfoControllerChangeMultipleInfoAddress() {
        return manager.getHostPort() + "/controller/method/user/change-multiple-info";
    }

    public static String getUserInfoControllerGetBalanceAddress() {
        return manager.getHostPort() + "/controller/method/user/get-balance";
    }

    public static String getUserInfoControllerGetRoleAddress() {
        return manager.getHostPort() + "/controller/method/user/get-role";
    }

}
