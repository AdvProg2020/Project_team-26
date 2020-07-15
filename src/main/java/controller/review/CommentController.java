package controller.review;

import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NoObjectIdException;
import model.Comment;
import model.Customer;
import model.Session;
import model.User;
import model.enums.CommentState;
import model.enums.Role;
import repository.CommentRepository;
import repository.ProductRepository;
import repository.RepositoryContainer;

import java.util.List;

public class CommentController {

    ProductRepository productRepository;
    CommentRepository commentRepository;

    public CommentController(RepositoryContainer repositoryContainer) {
        this.productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
        this.commentRepository = (CommentRepository) repositoryContainer.getRepository("CommentRepository");
    }

    public void addComment(String description, String title, int productId, String token) throws NoAccessException, InvalidTokenException {
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

    public void confirmComment(int id, String token) throws InvalidTokenException, NoAccessException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null || user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            Comment comment = commentRepository.getById(id);
            comment.setState(CommentState.CONFIRMED);
            commentRepository.save(comment);
        }
    }

    public void rejectComment(int id, String token) throws InvalidTokenException, NoAccessException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null || user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            Comment comment = commentRepository.getById(id);
            comment.setState(CommentState.NOT_CONFIRMED);
            commentRepository.save(comment);
        }
    }

    public List<Comment> getAllComments(String token) throws InvalidTokenException, NoAccessException {
        User user = Session.getSession(token).getLoggedInUser();
        if(user == null || user.getRole() != Role.ADMIN) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            return commentRepository.getAll();
        }
    }

    public List<Comment> getConfirmedComments(int productId, String token) {
        return commentRepository.getConfirmedComments(productId,null);
    }

    public void removeComment(int id, String token) throws NoAccessException, InvalidTokenException, NoObjectIdException {
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
