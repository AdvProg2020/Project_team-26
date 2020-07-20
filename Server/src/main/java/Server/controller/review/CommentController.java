package Server.controller.review;

import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NoObjectIdException;
import model.*;
import model.enums.CommentState;
import model.enums.Role;
import org.springframework.web.bind.annotation.*;
import repository.CommentRepository;
import repository.ProductRepository;
import repository.RepositoryContainer;

import java.util.List;
import java.util.Map;

@RestController
public class CommentController {

    ProductRepository productRepository;
    CommentRepository commentRepository;

    public CommentController() {
        this.productRepository = (ProductRepository) RepositoryContainer.getInstance().getRepository("ProductRepository");
        this.commentRepository = (CommentRepository) RepositoryContainer.getInstance().getRepository("CommentRepository");
    }


    @PostMapping("/controller/method/comment/add-comment")
    public void addComment(@RequestBody Map info) throws NoAccessException, InvalidTokenException {
        String description = (String) info.get("description");
        String title = (String) info.get("title");
        int productId = (Integer) info.get("productId");
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null || user.getRole() != Role.CUSTOMER) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            Comment comment =
                    new Comment((Customer) user, productRepository.getById(productId), description, title);
            comment.setState(CommentState.WAITING_FOR_CONFIRMATION);
            commentRepository.save(comment);
        }
    }

    @PostMapping("/controller/method/comment/confirm-comment")
    public void confirmComment(@RequestBody Map info) throws InvalidTokenException, NoAccessException {
        int id = (Integer) info.get("id");
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null || user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            Comment comment = commentRepository.getById(id);
            comment.setState(CommentState.CONFIRMED);
            commentRepository.save(comment);
        }
    }

    @PostMapping("/controller/method/comment/reject-comment")
    public void rejectComment(@RequestBody Map info) throws InvalidTokenException, NoAccessException {
        int id = (Integer) info.get("id");
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null || user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            Comment comment = commentRepository.getById(id);
            comment.setState(CommentState.NOT_CONFIRMED);
            commentRepository.save(comment);
        }
    }

    @PostMapping("/controller/method/comment/get-all-comments")
    public List<Comment> getAllComments(@RequestBody Map info) throws InvalidTokenException, NoAccessException {
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null || user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            return commentRepository.getAll();
        }
    }

    @RequestMapping("/controller/method/comment/get-confirmed-comments/{id}")
    public List<Comment> getConfirmedComments(@PathVariable("id") int productId) {
        return commentRepository.getConfirmedComments(productId,null);
    }

    @PostMapping("/controller/method/comment/remove-comment")
    public void removeComment(@RequestBody Map info) throws NoAccessException, InvalidTokenException, NoObjectIdException {
        int id = (Integer) info.get("id");
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null || user.getRole() == Role.SELLER) {
            throw new NoAccessException("You are not allowed to do that.");
        } else if (user.getRole() == Role.ADMIN) {
            commentRepository.delete(id);
        } else if (!commentRepository.getById(id).getCustomer().equals((Customer) user)) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            commentRepository.delete(id);
        }
    }

}
