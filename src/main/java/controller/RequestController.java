package controller;

import controller.interfaces.request.IRequestController;
import model.OffRequest;
import model.ProductRequest;
import model.ProductSellerRequest;

import java.util.List;

public class RequestController implements IRequestController {


    @Override
    public void acceptOffRequest(int requestId, String token) {

    }

    @Override
    public void rejectOffRequest(int requestId, String token) {

    }

    @Override
    public void acceptProductRequest(int requestId, String token) {

    }

    @Override
    public void rejectProductRequest(int requestId, String token) {

    }

    @Override
    public void acceptProductSellerRequest(int requestId, String token) {

    }

    @Override
    public void rejectProductSellerRequest(int requestId, String token) {

    }

    @Override
    public List<ProductRequest> getAllProductRequests(String sortField, boolean isAscending, String token) {
        return null;
    }

    @Override
    public List<OffRequest> getAllOffRequests(String sortField, boolean isAscending, String token) {
        return null;
    }

    @Override
    public List<ProductSellerRequest> getAllProductSellerRequests(String sortField, boolean isAscending, String token) {
        return null;
    }
}
