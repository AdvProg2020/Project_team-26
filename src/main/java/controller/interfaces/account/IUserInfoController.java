package controller.interfaces.account;

public interface IUserInfoController {

    String getEmail(String token);

    void changeEmail(String email, String token);

    String getUsername(String token);

    void changeUsername(String username, String token);

    String getFirstName(String token);

    String getLastName(String token);

    String getPassword(String token);

    void setPassword(String oldPassword, String newPassword, String token);

    void changePassword(String oldPassword, String newPassword, String token);

    int getInfo(String key, String token);

    void changeInfo(String key, String value, String token);

    String getCompanyName(String token);

    String getBalance(String token);

    String getRole(String token);
}
