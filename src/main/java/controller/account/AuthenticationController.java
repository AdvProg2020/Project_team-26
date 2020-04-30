package controller.account;

import controller.Exceptions;
import controller.interfaces.account.IAuthenticationController;

public class AuthenticationController implements IAuthenticationController {

    public void login(String username, String password, String token) throws Exceptions.FieldsExistWithSameName, Exceptions.InvalidPasswordException {


    }

    public void register(Account account , String token) throws Exceptions.FieldsExistWithSameName, Exceptions.InvalidAccessDemand {


    }

    public void logout(String token) throws Exceptions.UnSuccessfulLogout {

    }

}
