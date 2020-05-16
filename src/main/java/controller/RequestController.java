package controller;

import controller.interfaces.request.IRequestController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import model.*;
import repository.OffRepository;
import repository.ProductRepository;
import repository.ProductSellerRepository;
import repository.RepositoryContainer;

import java.util.List;

public class RequestController implements IRequestController {

    private OffRepository offRepository;
    private ProductSellerRepository productSellerRepository;
    private ProductRepository productRepository;

    public RequestController(RepositoryContainer repositoryContainer){
        this.offRepository = (OffRepository)repositoryContainer.getRepository("OffRepository");
        this.productSellerRepository = (ProductSellerRepository) repositoryContainer.getRepository("ProductSellerRepository");
        this.productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
    }


    @Override
    public void acceptOffRequest(int requestId, String token) throws InvalidTokenException, NoAccessException, NotLoggedINException {
        Session session = Session.getSession(token);
        if(session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (session.getLoggedInUser().getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {

        }

    }

    @Override
    public void rejectOffRequest(int requestId, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException {
        Session session = Session.getSession(token);
        if(session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (session.getLoggedInUser().getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {

        }
    }

    @Override
    public void acceptProductRequest(int requestId, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException {
        Session session = Session.getSession(token);
        if(session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (session.getLoggedInUser().getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            productRepository.addRequest();
        }
    }

    @Override
    public void rejectProductRequest(int requestId, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException {
        Session session = Session.getSession(token);
        if(session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (session.getLoggedInUser().getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {

        }
    }

    @Override
    public void acceptProductSellerRequest(int requestId, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException {
        Session session = Session.getSession(token);
        if(session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (session.getLoggedInUser().getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {

        }
    }

    @Override
    public void rejectProductSellerRequest(int requestId, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException {
        Session session = Session.getSession(token);
        if(session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (session.getLoggedInUser().getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {

        }
    }

    @Override
    public OffRequest getOffRequestById(int id, String token) {
        return null;
    }

    @Override
    public ProductRequest getProductRequestById(int id, String token) {
        return null;
    }

    @Override
    public ProductSellerRequest getProductSellerRequestById(int id, String token) {
        return null;
    }

    @Override
    public List<ProductRequest> getAllProductRequests(String sortField, boolean isAscending, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException {
        Session session = Session.getSession(token);
        if(session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (session.getLoggedInUser().getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            return productRepository.getAllRequests(sortField,isAscending);
        }
    }

    @Override
    public List<OffRequest> getAllOffRequests(String sortField, boolean isAscending, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException {
        Session session = Session.getSession(token);
        if(session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (session.getLoggedInUser().getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            return offRepository.getAllRequests(sortField,isAscending);
        }
    }

    @Override
    public List<ProductSellerRequest> getAllProductSellerRequests(String sortField, boolean isAscending, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException {
        Session session = Session.getSession(token);
        if(session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (session.getLoggedInUser().getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            return productSellerRepository.getAllRequests(sortField,isAscending);
        }
    }
}
