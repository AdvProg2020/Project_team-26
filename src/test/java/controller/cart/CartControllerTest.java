package controller.cart;

import controller.account.AuthenticationController;
import model.Session;
import model.repository.RepositoryContainer;
import model.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;

public class CartControllerTest {

    private RepositoryContainer repositoryContainer;
    private UserRepository userRepository;
    private String token;
    private AuthenticationController authenticationController;
    private CartController cartController;

    @BeforeEach
    public void setup() {
        repositoryContainer = new RepositoryContainer();
        token = Session.addSession();
        authenticationController = new AuthenticationController(repositoryContainer);
        cartController = new CartController(repositoryContainer);
        userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
    }
}
