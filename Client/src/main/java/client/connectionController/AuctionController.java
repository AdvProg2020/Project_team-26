package client.connectionController;

import client.connectionController.interfaces.auction.IAuctionController;
import client.exception.*;
import client.gui.Constants;
import client.model.*;
import com.google.gson.Gson;
import net.minidev.json.JSONObject;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AuctionController implements IAuctionController {

    public List<Auction> getAllAuction(String token) {
        RestTemplate restTemplate = new RestTemplate();
        Auction[] auctions = restTemplate.getForObject(Constants.getAuctionControllerGetAllAuctionAddress() + "/" + token, Auction[].class);
        return Arrays.asList(auctions);
    }

    public void createNewAuction(int productSellerId, Date endDate, String token) throws ObjectAlreadyExistException, NotLoggedINException, NotSellerException, InvalidTokenException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productSellerId", productSellerId);
        Gson gson = new Gson();
        String dateString = gson.toJson(endDate);
        jsonObject.put("endDate", dateString);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getAuctionControllerCreateNewAuctionAddress());
        } catch (HttpClientErrorException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case NotSellerException:
                    throw NotSellerException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case ObjectAlreadyExistException:
                    throw ObjectAlreadyExistException.getHttpException(e.getResponseBodyAsString());
                default:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }

    public void participateInAuction(int AuctionId, long price, String token) throws InvalidIdException, NotLoggedINException, InvalidTokenException, NotEnoughCreditException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("AuctionId", AuctionId);
        jsonObject.put("price", price);
        jsonObject.put("token", token);
        try {
            Constants.manager.postRequestWithVoidReturnType(jsonObject, Constants.getAuctionControllerParticipateInAuctionAddress());
        } catch (HttpClientErrorException e) {
            switch (HttpExceptionEquivalent.getEquivalentException(e.getRawStatusCode())) {
                case InvalidIdException:
                    throw InvalidIdException.getHttpException(e.getResponseBodyAsString());
                case InvalidTokenException:
                    throw InvalidTokenException.getHttpException(e.getResponseBodyAsString());
                case NotEnoughCreditException:
                    throw NotEnoughCreditException.getHttpException(e.getResponseBodyAsString());
                default:
                    throw NotLoggedINException.getHttpException(e.getResponseBodyAsString());
            }
        }
    }
}
