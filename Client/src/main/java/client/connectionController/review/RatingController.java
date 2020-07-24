package client.connectionController.review;


import client.connectionController.interfaces.review.IRatingController;
import client.exception.*;
import client.gui.Constants;
import net.minidev.json.JSONObject;
import org.springframework.web.client.UnknownHttpStatusCodeException;

public class RatingController implements IRatingController {

    public void rate(double rating, int productId, String token) throws NoAccessException, NotBoughtTheProductException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rating", rating);
        jsonObject.put("productId", productId);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getRatingControllerRateAddress());
        } catch (UnknownHttpStatusCodeException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NotBoughtTheProductException:
                    throw NotBoughtTheProductException.getHttpException(e.getResponseBodyAsString());
                case NoAccessException:
                    throw NoAccessException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }
}