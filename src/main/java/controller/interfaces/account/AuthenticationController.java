package controller.interfaces.account;

public interface AuthenticationController {

    void login(String username,String password, String token);

    void register(String username, String password, String email, String role, String token);

    void logout();
}
