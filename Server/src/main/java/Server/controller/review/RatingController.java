package Server.controller.review;

import exception.*;
import model.*;
import model.enums.Role;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import repository.ProductRepository;
import repository.RateRepository;
import repository.RepositoryContainer;
import repository.UserRepository;

import java.util.Map;

@RestController
public class RatingController {

    RateRepository rateRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

    public RatingController() {
        this.rateRepository = (RateRepository) RepositoryContainer.getInstance().getRepository("RatingRepository");
        this.userRepository = (UserRepository) RepositoryContainer.getInstance().getRepository("UserRepository");
        this.productRepository = (ProductRepository) RepositoryContainer.getInstance().getRepository("ProductRepository");
    }


    @PostMapping("/controller/method/rate/rate")
    public void rate(@RequestBody Map info) throws NoAccessException, NotBoughtTheProductException, InvalidTokenException {
        double rating = (Double) info.get("rating");
        int productId = (Integer) info.get("productId");
        String token = (String) info.get("token");
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