package client.connectionController.interfaces;

import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.exception.NotLoggedINException;

import java.io.IOException;

public interface IBankController {
    String createAccount(String userName,String password,String firstName,String lastName,String repeatedPass);
    String chargeAccount(int bankSourceAccountId,String description,long money,String token)throws InvalidTokenException, IOException, NotLoggedINException, NoAccessException;

}
