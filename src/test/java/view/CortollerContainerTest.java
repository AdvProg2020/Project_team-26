package view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.ControllerContainer.Controller.*;

import static view.ControllerContainer.Controller.*;

public class CortollerContainerTest {
    @Test
    void testAll() {
        ControllerContainer controllerContainer = new ControllerContainer();
        Assertions.assertEquals(controllerContainer.getController(AuthenticationController), controllerContainer.getController(AuthenticationController));
        Assertions.assertEquals(controllerContainer.getController(ShowUserController), controllerContainer.getController(ShowUserController));
        Assertions.assertEquals(controllerContainer.getController(SessionController), controllerContainer.getController(SessionController));
        Assertions.assertEquals(controllerContainer.getController(UserInfoController), controllerContainer.getController(UserInfoController));
        Assertions.assertEquals(controllerContainer.getController(CartController), controllerContainer.getController(CartController));
        Assertions.assertEquals(controllerContainer.getController(CategoryController), controllerContainer.getController(CategoryController));
        Assertions.assertEquals(controllerContainer.getController(OffController), controllerContainer.getController(OffController));
        Assertions.assertEquals(controllerContainer.getController(PromoController), controllerContainer.getController(PromoController));
        Assertions.assertEquals(controllerContainer.getController(OrderController), controllerContainer.getController(OrderController));
        Assertions.assertEquals(controllerContainer.getController(ProductController), controllerContainer.getController(ProductController));
        Assertions.assertEquals(controllerContainer.getController(RequestController), controllerContainer.getController(RequestController));
        Assertions.assertEquals(controllerContainer.getController(CommentController), controllerContainer.getController(CommentController));
        Assertions.assertEquals(controllerContainer.getController(RatingController), controllerContainer.getController(RatingController));
    }
}