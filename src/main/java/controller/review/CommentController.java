package controller.review;

import controller.interfaces.review.ICommentController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NoObjectIdException;
import model.*;
import repository.CommentRepository;
import repository.ProductRepository;
import repository.RepositoryContainer;

public class CommentController implements ICommentController {

    ProductRepository productRepository;
    CommentRepository commentRepository;

    public CommentController(RepositoryContainer repositoryContainer) {
        this.productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
        this.commentRepository = (CommentRepository) repositoryContainer.getRepository("CommentRepository");
    }

    @Override
    public void addComment(String comment, String title, int productId, String token) throws NoAccessException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null || user.getRole() != Role.CUSTOMER) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            commentRepository.save(new Comment((Customer) user, productRepository.getById(productId), comment, title));
        }
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
