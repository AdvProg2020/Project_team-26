package controller.review;

import controller.interfaces.review.ICommentController;
import exception.NoAccessException;
import model.*;
import model.repository.CommentRepository;
import model.repository.ProductRepository;
import model.repository.RepositoryContainer;

public class CommentController implements ICommentController {

    ProductRepository productRepository;
    CommentRepository commentRepository;

    public CommentController(RepositoryContainer repositoryContainer) {
        this.productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
        this.commentRepository = (CommentRepository) repositoryContainer.getRepository("CommentRepository");
    }

    public void addAComment(String comment, int productId, String token) throws NoAccessException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user.getRole() != Role.CUSTOMER) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            commentRepository.save(new Comment((Customer) user, productRepository.getById(productId), comment));
        }
    }

    public void removeComment(int id, String token) throws NoAccessException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null || user.getRole() == Role.SELLER) {
            throw new NoAccessException("You are not allowed to do that.");
        } else if (user.getRole() == Role.ADMIN) {
            commentRepository.delete(id);
        } else if (commentRepository.getById(id).getCustomer().equals((Customer) user)) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            commentRepository.delete(id);
        }
    }

}
