package controller.review;

import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotBoughtTheProductException;
import model.Customer;
import model.Rate;
import model.Session;
import model.User;
import model.enums.Role;
import repository.ProductRepository;
import repository.RateRepository;
import repository.RepositoryContainer;
import repository.UserRepository;

public class RatingController {

    RateRepository rateRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

    public RatingController(RepositoryContainer repositoryContainer) {
        this.rateRepository = (RateRepository) repositoryContainer.getRepository("RatingRepository");
        this.userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
        this.productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
    }

    public void rate(double rating, int productId, String token) throws NoAccessException, NotBoughtTheProductException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null || user.getRole() != Role.CUSTOMER) {
            throw new NoAccessException("You are not allowed to do that.");
        } else if (!userRepository.hasBoughtProduct(user.getId(), productId)) {
            throw new NotBoughtTheProductException("You have not bought this product");
        } else {
            Rate previousRate = rateRepository.getCustomerRate(user.getId(), productId);
            if(previousRate == null) {
                rateRepository.save(new Rate((Customer) user, rating, productRepository.getById(productId)));
            } else {
                previousRate.setScore(rating);
                rateRepository.save(previousRate);
            }
        }
    }
}