package client;

import java.util.Map;

public class ControllerContainer {
    public enum Controller {
        AuthenticationController, SessionController, ShowUserController, UserInfoController,
        CartController, CategoryController, OffController, PromoController, OrderController, ProductController,
        RequestController, CommentController, RatingController;
    }

    private Map<ControllerContainer.Controller, Object> map;
    public Object getController(ControllerContainer.Controller controller) {
        return map.get(controller);
    }

}
