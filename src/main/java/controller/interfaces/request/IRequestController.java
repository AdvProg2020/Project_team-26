package controller.interfaces.request;

import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import model.OffRequest;
import model.ProductRequest;
import model.ProductSellerRequest;

import java.util.List;

public interface IRequestController {

    public void acceptOffRequest(int requestId,String token) throws InvalidTokenException, NoAccessException, NotLoggedINException;
    public void rejectOffRequest(int requestId,String token) throws NotLoggedINException, NoAccessException, InvalidTokenException;
    public void acceptProductRequest(int requestId,String token) throws NotLoggedINException, NoAccessException, InvalidTokenException;
    public void rejectProductRequest(int requestId,String token) throws NoAccessException, NotLoggedINException, InvalidTokenException;
    public void acceptProductSellerRequest(int requestId,String token) throws NotLoggedINException, NoAccessException, InvalidTokenException;
    public void rejectProductSellerRequest(int requestId,String token) throws NoAccessException, NotLoggedINException, InvalidTokenException;
    public OffRequest getOffRequestById(int id,String token) throws NoAccessException, NotLoggedINException, InvalidTokenException;
    public ProductRequest getProductRequestById(int id,String token) throws NoAccessException, NotLoggedINException, InvalidTokenException;
    public ProductSellerRequest getProductSellerRequestById(int id,String token) throws NotLoggedINException, NoAccessException, InvalidTokenException;
    public List<ProductRequest> getAllProductRequests(String sortField, boolean isAscending, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException;
    public List<OffRequest> getAllOffRequests(String sortField, boolean isAscending, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException;
    public List<ProductSellerRequest> getAllProductSellerRequests(String sortField, boolean isAscending, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException;
    public List<ProductRequest> getAllProductRequests(String sortField, boolean isAscending,int startIndex,int endIndex, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException;
    public List<OffRequest> getAllOffRequests(String sortField, boolean isAscending,int startIndex,int endIndex, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException;
    public List<ProductSellerRequest> getAllProductSellerRequests(String sortField, boolean isAscending,int startIndex,int endIndex, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException;

}
