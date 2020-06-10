package controller;

import controller.interfaces.request.IRequestController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import model.*;
import repository.*;

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
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            offRepository.acceptRequest(requestId);
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
            offRepository.rejectRequest(requestId);
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
            productRepository.acceptRequest(requestId);
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
            productRepository.rejectRequest(requestId);
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
            productSellerRepository.acceptRequest(requestId);
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
            productSellerRepository.rejectRequest(requestId);
        }
    }

    @Override
    public OffRequest getOffRequestById(int id, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            return offRepository.getOffRequestById(id);
        }
    }

    @Override
    public ProductRequest getProductRequestById(int id, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            return productRepository.getProductRequestById(id);
        }
    }

    @Override
    public ProductSellerRequest getProductSellerRequestById(int id, String token) throws NotLoggedINException, NoAccessException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            return productSellerRepository.getProductSellerRequestById(id);
        }
    }

    @Override
    public List<ProductRequest> getAllProductRequests(String sortField, boolean isAscending, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException {
        Pageable page = createAPage(sortField,isAscending,0,0);
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            return productRepository.getAllRequests(page);
        }
    }

    @Override
    public List<OffRequest> getAllOffRequests(String sortField, boolean isAscending, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException {
        Pageable page = createAPage(sortField,isAscending,0,0);
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            return offRepository.getAllRequests(page);
        }
    }

    @Override
    public List<ProductSellerRequest> getAllProductSellerRequests(String sortField, boolean isAscending, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException {
        Pageable page = createAPage(sortField,isAscending,0,0);
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            return productSellerRepository.getAllRequests(page);
        }
    }

    @Override
    public List<ProductRequest> getAllProductRequests(String sortField, boolean isAscending, int startIndex, int endIndex, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException {
        Pageable page = createAPage(sortField,isAscending,startIndex,endIndex);
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            return productRepository.getAllRequests(page);
        }
    }

    @Override
    public List<OffRequest> getAllOffRequests(String sortField, boolean isAscending, int startIndex, int endIndex, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException {
        Pageable page = createAPage(sortField,isAscending,startIndex,endIndex);
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            return offRepository.getAllRequests(page);
        }
    }

    @Override
    public List<ProductSellerRequest> getAllProductSellerRequests(String sortField, boolean isAscending, int startIndex, int endIndex, String token) throws NoAccessException, NotLoggedINException, InvalidTokenException {
        Pageable page = createAPage(sortField,isAscending,startIndex,endIndex);
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            return productSellerRepository.getAllRequests(page);
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
