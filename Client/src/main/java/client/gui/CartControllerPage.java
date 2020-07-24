package client.gui;

import client.ControllerContainer;
import client.connectionController.interfaces.IBankController;
import client.connectionController.interfaces.cart.ICartController;
import client.exception.*;
import client.gui.interfaces.InitializableController;
import client.gui.interfaces.Reloadable;
import client.gui.product.SingleProductOfCartController;
import client.model.Cart;
import client.model.ProductSeller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Map;

public class CartControllerPage implements InitializableController, Reloadable {
    private Cart cart;
    private ICartController cartController;
    @FXML
    VBox cartProductsVBox;
    @FXML
    Button purchaseButton;
    @FXML
    Text totalPriceText;
    @FXML
    Label promoLabel;
    @FXML
    TextField addressTextField;
    @FXML
    TextField promoTextField;
    @FXML
    private TextField bankIDTextField;
    @FXML
    private CheckBox bankCheckBox;
    @FXML
    private Label errorLabel;
    private IBankController bankController;


    @Override
    public void initialize(int id) throws IOException, InvalidTokenException {
        cartController = (ICartController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.CartController);
        bankController = (IBankController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.BankController);
        cart = cartController.getCart(Constants.manager.getToken());
        totalPriceText.setText("total price");//todo
        bankIDTextField.setVisible(false);
        bankCheckBox.setOnAction(e -> {
            bankCheckBoxAction();
        });
        loadCart(cart);
    }

    private void bankCheckBoxAction() {
        if (bankCheckBox.isSelected()) {
            bankIDTextField.setVisible(true);
            return;
        }
        bankIDTextField.setVisible(false);
    }

    private void loadCart(Cart cart) throws IOException {
        try {
            totalPriceText.setText("" + cartController.getTotalPrice(Constants.manager.getToken()));
        } catch (InvalidTokenException e) {
            e.printStackTrace();//TODO
        }

        loadProducts(cart);
    }

    private void loadProducts(Cart cart) throws IOException {
        cartProductsVBox.getChildren().removeAll(cartProductsVBox.getChildren());
        Map<ProductSeller, Integer> products = cart.getProducts();
        products.forEach((key, value) -> {
            if (value > 0) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ProductOfCart.fxml"));
                try {
                    Node node = loader.load();
                    SingleProductOfCartController singleProductOfCartController = (SingleProductOfCartController) loader.getController();
                    singleProductOfCartController.initialize(key.getId());
                    singleProductOfCartController.load(key, value, this::reload);
                    cartProductsVBox.getChildren().add(node);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    public void purchaseButtonClicked() throws IOException {
        if (addressTextField.getText().isBlank()) {
            errorLabel.setText("address is empty");
            return;
        }
        try {
            cartController.setAddress(addressTextField.getText(), Constants.manager.getToken());
            if (!promoTextField.getText().isBlank() && !promoTextField.getText().equals(""))
                cartController.usePromoCode(promoTextField.getText(), Constants.manager.getToken());
            if (bankCheckBox.isSelected()) {
                chargeTheAccountForCheckOutFromBank();
            } else {
                finalPurchase();
            }
        } catch (InvalidPromoCodeException | NoAccessException | PromoNotAvailableException | NotLoggedINException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        } catch (InvalidTokenException e) {
            Constants.manager.showSuccessPopUp(e.getMessage());
            Constants.manager.setTokenFromBankForBankTransaction();
        }
    }

    private void finalPurchase() throws IOException, NoAccessException, NotLoggedINException {
        try {
            cartController.checkout(Constants.manager.getToken());
            Constants.manager.showRandomPromoIfUserGet();
            Constants.manager.showSuccessPopUp("Purchased");
            Constants.manager.openPage("AllProducts", 0);
        } catch (InvalidTokenException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
            Constants.manager.showLoginMenu();
        } catch (NotEnoughCreditException | NotEnoughProductsException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        }
    }

    private void chargeTheAccountForCheckOutFromBank() throws IOException {
        if (Constants.manager.checkInputIsInt(bankIDTextField.getText())) {
            int accountId = Integer.parseInt(bankIDTextField.getText());
            String description = "cart charge";
            try {
                String message = bankController.chargeAccount(accountId, description, cartController.getTotalPrice(Constants.manager.getToken()), Constants.manager.getToken());
                if (message.equals(Constants.bankErrorInvalidٍSource)) {
                    errorLabel.setText("your bank account id is invalid");
                    return;
                }
                if (message.equals(Constants.bankErrorInvalidToken) || message.equals(Constants.bankErrorExpiredToken)) {
                    errorLabel.setText("sorry error happened from server enter your bank account info");
                    Constants.manager.setTokenFromBankForBankTransaction();
                    return;
                }
                if(message.equals(Constants.bankErrorNotEnoughInSource)){
                    Constants.manager.showErrorPopUp(Constants.bankErrorNotEnoughInSource);
                    return;
                }
                finalPurchase();
            } catch (InvalidTokenException e) {
                Constants.manager.showErrorPopUp(e.getMessage());
                Constants.manager.showLoginMenu();
            } catch (NotLoggedINException | NoAccessException e) {
                errorLabel.setText(e.getMessage());
            }
        } else {
            errorLabel.setText("invalid bankAccount id format");
        }
    }

    @Override
    public void reload() throws IOException {
        cartProductsVBox.getChildren().removeAll(cartProductsVBox.getChildren());
        loadCart(this.cart);
    }
}
