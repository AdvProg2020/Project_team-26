package controller.account;

import controller.interfaces.account.IUserInfoController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NoSuchField;
import exception.NotLoggedINException;
import model.Role;
import model.Seller;
import model.Session;

public class UserInfoController implements IUserInfoController {


    public void changePassword(String oldPassword, String newPassword, String token) throws InvalidTokenException, NoAccessException, NotLoggedINException {
        Session session = Session.getSession(token);
        if(session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in.");
        } else {
            session.getLoggedInUser().changePassword(oldPassword,newPassword);
        }
    }

    public void changeInfo(String key, String value, String token) {
    }

    @Override
    public String getCompanyName(String token) throws InvalidTokenException, NotLoggedINException, NoSuchField {
        Session session = Session.getSession(token);
        if(session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not Logged in.");
        } else if (session.getLoggedInUser().getRole() != Role.SELLER) {
            throw new NoSuchField("This field does not exist.");
        } else {
            return ((Seller)session.getLoggedInUser()).getCompanyName();
        }
    }

    @Override
    public String getBalance(String token) throws NotLoggedINException, InvalidTokenException {
        Session session = Session.getSession(token);
        if(session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in");
        } else {
            return String.valueOf(session.getLoggedInUser().getCredit());
        }
    }

    public String getRole(String token) throws NotLoggedINException, InvalidTokenException {
        Session session = Session.getSession(token);
        if(session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in");
        } else {
            return session.getLoggedInUser().getRole().toString();
        }
    }
}
