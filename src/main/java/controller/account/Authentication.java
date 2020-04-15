package controller.account;

public class Authentication {

    public String loginClient(String username,String password) {
        return username;
    }

    public RegisterForm getRegisterForm(String username,String password) {
        return new RegisterForm();
    }

    public void registerClient(RegisterForm registerForm) {

    }

    public RegisterForm createManager(String username,String password) {
        return new RegisterForm();
    }

    public void logout() {

    }
}
