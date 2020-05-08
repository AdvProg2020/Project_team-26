package controller.review;

import controller.interfaces.review.IRatingController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotBoughtTheProductException;
import model.*;
import model.repository.ProductRepository;
import model.repository.RatingRepository;
import model.repository.RepositoryContainer;
import model.repository.UserRepository;

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
        if(user.getRole() != Role.CUSTOMER) {
            throw new NoAccessException("You are not allowed to do that.");
        } else if(!userRepository.hasBoughtProduct(user.getId(),productId)) {
            throw new NotBoughtTheProductException("You have not bought this product");
        } else {
            ratingRepository.save(new Rate((Customer) user, rating, productRepository.getById(productId)));
        }
    }

    public void removeRating(int id, String token) throws NoAccessException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if(user.getRole() == Role.ADMIN) {
            ratingRepository.delete(id);
        }else if (user.getRole() == Role.SELLER) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            if(!ratingRepository.doesItMatch(id,user.getId())) {
                throw new NoAccessException("You are not allowed to do that.");
            } else {
                ratingRepository.delete(id);
            }
        }
    }

    public void editRating(int id, double  newRating, String token) throws NoAccessException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if(user.getRole() == Role.ADMIN) {
            ratingRepository.delete(id);
        }else if (user.getRole() == Role.SELLER) {
            throw new NoAccessException("You are not allowed to do that.");
        } else {
            if(!ratingRepository.doesItMatch(id,user.getId())) {
                throw new NoAccessException("You are not allowed to do that.");
            } else {
                ratingRepository.editRate(id,newRating);
            }
        }
    }
}
