package client.gui.customer;

import client.ControllerContainer;
import client.connectionController.interfaces.auction.IAuctionController;
import client.connectionController.interfaces.product.IProductController;
import client.exception.*;
import client.gui.ChatPageController;
import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import client.model.Auction;
import client.model.Message;
import client.model.Product;
import client.model.User;
import client.model.enums.MessageType;
import client.model.enums.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Date;

public class AuctionPageController implements InitializableController {
    private int userId;
    private User thisUser;
    private IAuctionController auctionController;
    private IProductController productController;
    private Integer auctionId;
    @FXML
    private TextField cashTextField;
    @FXML
    private Button sendButton;
    @FXML
    private TableView<Auction> auctionTable;
    @FXML
    private TableColumn<Date, Auction> auctionColumns;
    @FXML
    private VBox chatBox;
    @FXML
    private TextArea descriptionOfProduct;
    @FXML
    private Label errorLabel;

    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException, InvalidIdException {
        auctionController = (IAuctionController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.AuctionController);
        productController = (IProductController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ProductController);
        auctionId = null;
        userId = id;
        descriptionOfProduct.setEditable(false);
        sendButton.setVisible(false);
        cashTextField.setVisible(false);
        auctionColumns.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        auctionTable.setOnMouseClicked(e -> {
            ObservableList<Auction> objects = auctionTable.getSelectionModel().getSelectedItems();
            if (objects == null || objects.size() == 0)
                return;
            try {
                handleAuction(objects.get(0));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        sendButton.setOnAction(e -> {
            sendRequestToServer();
        });
    }

    public void load(User thisUser) {
        this.thisUser = thisUser;
        auctionTable.setItems(FXCollections.observableList(auctionController.getAllAuction(Constants.manager.getToken())));
    }

    private void handleAuction(Auction auction) throws IOException {
        auctionId = auction.getId();
        cashTextField.setText("");
        sendButton.setVisible(true);
        cashTextField.setVisible(true);
        Product product = productController.getProductByProductSellerId(auction.getProductSeller().getId());
        descriptionOfProduct.setText(product.littleDescription());
        loadChatRoom("" + auction.getId());
    }

    private void sendRequestToServer() {
        String cash = cashTextField.getText();
        if (Constants.manager.checkIsLong(cash)) {
            try {
                auctionController.participateInAuction(auctionId, Long.parseLong(cash), Constants.manager.getToken());
                Constants.manager.showSuccessPopUp("your suggested cash accepted");
            } catch (InvalidIdException | NotEnoughCreditException e) {
                errorLabel.setText(e.getMessage());
            } catch (NotLoggedINException e) {
                //TODO
            } catch (InvalidTokenException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("enter number");
        }
    }

    private void loadChatRoom(String auctionId) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ChatPage.fxml"));
        Node node = loader.load();
        ChatPageController chatPageController = (ChatPageController) loader.getController();
        chatPageController.initialize(thisUser.getId());
        Constants.manager.sendMessageTOWebSocket(auctionId, new Message(thisUser.getUsername(), "im here", auctionId, MessageType.JOIN, Role.CUSTOMER));
        chatPageController.load(thisUser.getUsername(), auctionId, Role.CUSTOMER, false);
        chatBox.getChildren().removeAll(chatBox.getChildren());
        chatBox.getChildren().addAll(node);
    }
}
