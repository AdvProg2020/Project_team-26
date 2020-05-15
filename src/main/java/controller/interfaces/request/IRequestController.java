package controller.interfaces.request;

import model.OffRequest;
import model.ProductRequest;
import model.ProductSellerRequest;

import java.util.List;

public interface IRequestController {

    public void acceptOffRequest(int requestId,String token);
    public void rejectOffRequest(int requestId,String token);
    public void acceptProductRequest(int requestId,String token);
    public void rejectProductRequest(int requestId,String token);
    public void acceptProductSellerRequest(int requestId,String token);
    public void rejectProductSellerRequest(int requestId,String token);
    public List<ProductRequest> getAllProductRequests(String sortField, boolean isAscending, String token);
    public List<OffRequest> getAllOffRequests(String sortField, boolean isAscending, String token);
    public List<ProductSellerRequest> getAllProductSellerRequests(String sortField, boolean isAscending, String token);

}
