package client;

import java.util.Map;

public class ControllerContainer {
    public enum Controller {
        AuthenticationController, SessionController, ShowUserController, UserInfoController,
        CartController, CategoryController, OffController, PromoController, OrderController, ProductController,
        RequestController, CommentController, RatingController;
    }

    private Map<view.cli.ControllerContainer.Controller, Object> map;
    public Object getController(view.cli.ControllerContainer.Controller controller) {
        return map.get(controller);
    }

}
