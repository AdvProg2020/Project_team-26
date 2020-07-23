package client;


import client.connectionController.AuctionController;
import client.connectionController.RequestController;
import client.connectionController.SessionController;
import client.connectionController.account.*;
import client.connectionController.cart.CartController;
import client.connectionController.discount.OffController;
import client.connectionController.discount.PromoController;
import client.connectionController.order.OrderController;
import client.connectionController.product.CategoryController;
import client.connectionController.product.ProductController;
import client.connectionController.review.RatingController;
import client.connectionController.review.CommentController;

import java.util.HashMap;
import java.util.Map;

public class ControllerContainer {
    private Map<ControllerContainer.Controller, Object> map;

    public ControllerContainer() {
        map = new HashMap<>();
        initialize();
    }

    public enum Controller {
        AuthenticationController, SessionController, ShowUserController, UserInfoController,
        CartController, CategoryController, OffController, PromoController, OrderController, ProductController,
        RequestController, CommentController, RatingController, AuctionController;
    }


    public Object getController(ControllerContainer.Controller controller) {
        return map.get(controller);
    }

    private void initialize() {
        map.put(Controller.AuthenticationController, new AuthenticationController());
        map.put(Controller.SessionController, new SessionController());
        map.put(Controller.ShowUserController, new ShowUserController());
        map.put(Controller.UserInfoController, new UserInfoController());
        map.put(Controller.CartController, new CartController());
        map.put(Controller.CategoryController, new CategoryController());
        map.put(Controller.OffController, new OffController());
        map.put(Controller.PromoController, new PromoController());
        map.put(Controller.OrderController, new OrderController());
        map.put(Controller.ProductController, new ProductController());
        map.put(Controller.RequestController, new RequestController());
        map.put(Controller.CommentController, new CommentController());
        map.put(Controller.RatingController, new RatingController());
        map.put(Controller.AuctionController, new AuctionController());
    }
}
