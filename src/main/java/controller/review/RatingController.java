package controller.review;

import controller.interfaces.review.IRatingController;
import exception.*;
import model.*;
import repository.ProductRepository;
import repository.RatingRepository;
import repository.RepositoryContainer;
import repository.UserRepository;

public class RatingController implements IRatingController {

    RatingRepository ratingRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

    public RatingController(RepositoryContainer repositoryContainer) {
        this.ratingRepository = (RatingRepository) repositoryContainer.getRepository("RatingRepository");
        this.userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
        this.productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
    }

    public void addARating(double rating, int productId, String token) throws NoAccessException, NotBoughtTheProductException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null || user.getRole() != Role.CUSTOMER) {
            throw new NoAccessException("You are not allowed to do that.");
        } else if (!userRepository.hasBoughtProduct(user.getId(), productId)) {
            throw new NotBoughtTheProductException("You have not bought this product");
        } else {
            ratingRepository.save(new Rate((Customer) user, rating, productRepository.getById(productId)));
        }
    }

    public void removeRating(int id, String token) throws NoAccessException, InvalidTokenException, NotLoggedINException, NoObjectIdException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not Logged in.");
        } else if (user.getRole() == Role.ADMIN) {
            ratingRepository.delete(id);
        } else if (user.getRole() == Role.SELLER) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            if (!ratingRepository.doesItMatch(id, user.getId())) {
                throw new NoAccessException("You are not allowed to do that.");
            } else {
                ratingRepository.delete(id);
            }
        }
    }

    public void editRating(int id, double newRating, String token) throws NoAccessException, InvalidTokenException, NotLoggedINException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not Logged in.");
        } else if (user.getRole() != Role.CUSTOMER) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            if (!ratingRepository.doesItMatch(id, user.getId())) {
                throw new NoAccessException("You are not allowed to do that.");
            } else {
                ratingRepository.editRate(id, newRating);
            }
        }
    }
}
