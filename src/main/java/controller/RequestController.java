package controller;

import controller.interfaces.request.IRequestController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import model.*;
import model.enums.Role;
import repository.*;

import java.util.List;

public class RequestController implements IRequestController {

    private OffRepository offRepository;
    private ProductSellerRepository productSellerRepository;
    private ProductRepository productRepository;
    private RequestRepository requestRepository;

    public RequestController(RepositoryContainer repositoryContainer){
        this.offRepository = (OffRepository)repositoryContainer.getRepository("OffRepository");
        this.productSellerRepository = (ProductSellerRepository) repositoryContainer.getRepository("ProductSellerRepository");
        this.productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
        this.requestRepository = (RequestRepository) repositoryContainer.getRepository("RequestRepository");
    }


    @Override
    public void acceptOffRequest(int requestId, String token) throws InvalidTokenException, NoAccessException, NotLoggedINException {
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            requestRepository.accept(requestId);
        }

    }

    @Override
    public void rejectOffRequest(int requestId, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            requestRepository.reject(requestId);
        }
    }

    @Override
    public void acceptProductRequest(int requestId, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            requestRepository.accept(requestId);
        }
    }

    @Override
    public void rejectProductRequest(int requestId, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            requestRepository.reject(requestId);
        }
    }

    @Override
    public void acceptProductSellerRequest(int requestId, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            requestRepository.accept(requestId);
        }
    }

    @Override
    public void rejectProductSellerRequest(int requestId, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            requestRepository.reject(requestId);
        }
    }


    @Override
    public List<Request> getAllRequests(String sortField, boolean isAscending, int startIndex, int endIndex, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException {
        Pageable page = createAPage(sortField,isAscending,0,0);
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            return requestRepository.getAllPending(page);
        }
    }


    private Pageable createAPage(String sortField, boolean isAscending, int startIndex, int endIndex) {
        if(isAscending) {
            return new Pageable(startIndex,endIndex - startIndex,sortField, Pageable.Direction.ASCENDING);
        } else {
            return new Pageable(startIndex,endIndex - startIndex,sortField, Pageable.Direction.DESCENDING);
        }
    }
}
