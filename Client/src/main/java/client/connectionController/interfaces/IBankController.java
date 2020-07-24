package client.connectionController.interfaces;

import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.exception.NotEnoughCreditException;
import client.exception.NotLoggedINException;

public interface IBankController {
    String createAccount(String userName, String password, String firstName, String lastName, String repeatedPass);

    String chargeAccount(int bankSourceAccountId, String description, long money, String token) throws InvalidTokenException, NotLoggedINException, NoAccessException;

    String withdrawFromAccount(int bankSourceAccountId, String description, long money, String token) throws InvalidTokenException, NotLoggedINException, NoAccessException, NotEnoughCreditException;

    String getToken(String username, String password, String token) throws InvalidTokenException;
}