package client.connectionController.review;


import client.connectionController.interfaces.review.IRatingController;
import client.exception.*;
import client.gui.Constants;
import client.model.*;
import client.model.enums.Role;
import net.minidev.json.JSONObject;
import org.springframework.web.client.HttpClientErrorException;

public class RatingController implements IRatingController {

    public void rate(double rating, int productId, String token) throws NoAccessException, NotBoughtTheProductException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rating", rating);
        jsonObject.put("productId", productId);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getRateAddress());
        } catch (HttpClientErrorException e) {
            throw new NoAccessException("ksamd");
        }
    }
}