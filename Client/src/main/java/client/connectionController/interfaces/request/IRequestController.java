package client.connectionController.interfaces.request;


import client.exception.InvalidTokenException;
import client.exception.*;
import client.model.Request;

import java.util.List;

public interface IRequestController {

    public void acceptOffRequest(int requestId, String token) throws InvalidTokenException, NoAccessException, NotLoggedINException;
    public void rejectOffRequest(int requestId, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException;
    public void acceptProductRequest(int requestId, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException;
    public void rejectProductRequest(int requestId, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException;
    public void acceptProductSellerRequest(int requestId, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException;
    public void rejectProductSellerRequest(int requestId, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException;
    public List<Request> getAllRequests(String sortField, boolean isAscending, int startIndex, int endIndex, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException;

}
