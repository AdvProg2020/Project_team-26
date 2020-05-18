package controller.review;

import controller.interfaces.review.IRatingController;
import exception.*;
import model.*;
import repository.ProductRepository;
import repository.RateRepository;
import repository.RepositoryContainer;
import repository.UserRepository;

public class RatingController implements IRatingController {

    RateRepository rateRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

    public RatingController(RepositoryContainer repositoryContainer) {
        this.rateRepository = (RateRepository) repositoryContainer.getRepository("RatingRepository");
        this.userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
        this.productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
    }

    public void addRating(double rating, int productId, String token) throws NoAccessException, NotBoughtTheProductException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null || user.getRole() != Role.CUSTOMER) {
            throw new NoAccessException("You are not allowed to do that.");
        } else if (!userRepository.hasBoughtProduct(user.getId(), productId)) {
            throw new NotBoughtTheProductException("You have not bought this product");
        } else {
            rateRepository.save(new Rate((Customer) user, rating, productRepository.getById(productId)));
        }
    }
}
