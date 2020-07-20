package Server.controller;

import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import model.*;
import model.enums.Role;
import org.springframework.web.bind.annotation.*;
import repository.*;

import java.util.List;
import java.util.Map;

@RestController
public class RequestController {

    private OffRepository offRepository;
    private ProductSellerRepository productSellerRepository;
    private ProductRepository productRepository;
    private RequestRepository requestRepository;

    public RequestController() {
        this.offRepository = (OffRepository)RepositoryContainer.getInstance().getRepository("OffRepository");
        this.productSellerRepository = (ProductSellerRepository) RepositoryContainer.getInstance().getRepository("ProductSellerRepository");
        this.productRepository = (ProductRepository) RepositoryContainer.getInstance().getRepository("ProductRepository");
        this.requestRepository = (RequestRepository) RepositoryContainer.getInstance().getRepository("RequestRepository");
    }
    @PostMapping("/controller/method/request/accept-off-request")
    public void acceptOffRequest(@RequestBody Map info) throws InvalidTokenException, NoAccessException, NotLoggedINException {
        int requestId = (Integer) info.get("requestId");
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            requestRepository.accept(requestId);
        }

    }

    @PostMapping("/controller/method/request/reject-off-request")
    public void rejectOffRequest(@RequestBody Map info) throws NotLoggedINException, NoAccessException, InvalidTokenException {
        int requestId = (Integer) info.get("requestId");
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            requestRepository.reject(requestId);
        }
    }

    @PostMapping("/controller/method/request/accept-product-request")
    public void acceptProductRequest(@RequestBody Map info) throws NotLoggedINException, NoAccessException, InvalidTokenException {
        int requestId = (Integer) info.get("requestId");
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            requestRepository.accept(requestId);
        }
    }

    @PostMapping("/controller/method/request/reject-product-request")
    public void rejectProductRequest(@RequestBody Map info) throws NoAccessException, NotLoggedINException, InvalidTokenException {
        int requestId = (Integer) info.get("requestId");
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            requestRepository.reject(requestId);
        }
    }
    @PostMapping("/controller/method/request/accept-product-seller-request")
    public void acceptProductSellerRequest(@RequestBody Map info) throws NotLoggedINException, NoAccessException, InvalidTokenException {
        int requestId = (Integer) info.get("requestId");
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            requestRepository.accept(requestId);
        }
    }

    @PostMapping("/controller/method/request/reject-product-seller-request")
    public void rejectProductSellerRequest(@RequestBody Map info) throws NoAccessException, NotLoggedINException, InvalidTokenException {
        int requestId = (Integer) info.get("requestId");
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            requestRepository.reject(requestId);
        }
    }

    @PostMapping("/controller/method/request/get-all-requests")
    public List<Request> getAllRequests(@RequestBody Map info) throws NotLoggedINException, NoAccessException, InvalidTokenException {
        String sortField = (String) info.get("sortField");
        boolean isAscending = (Boolean) info.get("isAscending");
        int startIndex = (Integer) info.get("startIndex");
        int endIndex = (Integer) info.get("endIndex");
        String token = (String) info.get("token");
        Pageable page = createAPage(sortField,isAscending,startIndex,endIndex);
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null) {
            throw new NotLoggedINException("You are not logged in");
        } else if (user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You must be an Admin to do this.");
        } else {
            return requestRepository.getAllPending(page);
        }
    }

    @GetMapping("/controller/method/request/get-request-to-string/{id}")
    public String getRequestToString(@PathVariable("id")int id) {
        String toString = requestRepository.getById(id).toString();
        return toString;
    }


    private Pageable createAPage(String sortField, boolean isAscending, int startIndex, int endIndex) {
        if(isAscending) {
            return new Pageable(startIndex,endIndex - startIndex,sortField, Pageable.Direction.ASCENDING);
        } else {
            return new Pageable(startIndex,endIndex - startIndex,sortField, Pageable.Direction.DESCENDING);
        }
    }
}
