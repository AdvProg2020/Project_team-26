package view.cli;

import controller.product.ProductController;
import controller.RequestController;
import controller.SessionController;
import controller.account.AuthenticationController;
import controller.account.ShowUserController;
import controller.account.UserInfoController;
import controller.cart.CartController;
import controller.product.CategoryController;
import controller.discount.OffController;
import controller.discount.PromoController;
import controller.order.OrderController;
import controller.review.CommentController;
import controller.review.RatingController;
import repository.RepositoryContainer;

import java.util.HashMap;
import java.util.Map;

public class ControllerContainer {
    public enum Controller {
        AuthenticationController, SessionController, ShowUserController, UserInfoController,
        CartController, CategoryController, OffController, PromoController, OrderController, ProductController,
        RequestController, CommentController, RatingController;
    }

    private Map<Controller, Object> map;
    RepositoryContainer repository;

    public ControllerContainer(RepositoryContainer repositoryContainer) {
        map = new HashMap<>();
        repository = repositoryContainer;
        initializeFake();
    }

    private void initializeFake() {
        map.put(Controller.AuthenticationController, new AuthenticationController(repository));
        map.put(Controller.SessionController, new SessionController(repository));
        map.put(Controller.ShowUserController, new ShowUserController(repository));
        map.put(Controller.UserInfoController, new UserInfoController(repository));
        map.put(Controller.CartController, new CartController(repository));
        map.put(Controller.CategoryController, new CategoryController(repository));
        map.put(Controller.OffController, new OffController(repository));
        map.put(Controller.PromoController, new PromoController(repository));
        map.put(Controller.OrderController, new OrderController(repository));
        map.put(Controller.ProductController, new ProductController(repository));
        map.put(Controller.RequestController, new RequestController(repository));
        map.put(Controller.CommentController, new CommentController(repository));
        map.put(Controller.RatingController, new RatingController(repository));
    }

    public Object getController(Controller controller) {
        return map.get(controller);
    }

}
