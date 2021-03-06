package client.gui.admin;


import client.connectionController.interfaces.discount.IPromoController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import client.connectionController.interfaces.account.*;
import client.exception.*;
import client.gui.Constants;
import client.gui.interfaces.*;
import client.model.*;
import client.ControllerContainer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PromoManagerController implements InitializableController {


    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TextField codeText;
    @FXML
    private TextField offPercent;
    @FXML
    private TextField usernameText;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private TableView usernameTable;
    @FXML
    private TableView promoTable;
    @FXML
    private Label errorLabel;
    @FXML
    private Button addButtonUsername;
    @FXML
    private TextField maximumOff;

    private List<Promo> allPromos = new ArrayList<>();
    private IPromoController controller;
    private IShowUserController showController;


    public void initialize(int id) throws InvalidTokenException, NoAccessException {
        ObservableList list;
        controller = (IPromoController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.PromoController);
        showController = (IShowUserController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ShowUserController);
        addButton.setOnMouseClicked(e -> add());
        addButtonUsername.setOnMouseClicked(e -> addAUser());
        updateButton.setOnMouseClicked(e -> updateExistingPromo());
        promoTable.setOnMouseClicked(e -> fillDetails());
        try {
            refreshTable();
        } catch (NotLoggedINException e) {
            e.printStackTrace();
        }
    }

    private void fillDetails() {
        Promo promo = (Promo) promoTable.getSelectionModel().getSelectedItem();
        startDate.setValue(Constants.manager.getLocalDateFromDate(promo.getStartDate()));
        endDate.setValue(Constants.manager.getLocalDateFromDate(promo.getEndDate()));
        codeText.setText(promo.getPromoCode());
        offPercent.setText(Double.toString(promo.getPercent()));
        ObservableList<User> customers = FXCollections.observableList(promo.getCustomers());
        usernameTable.setItems(customers);
        ObservableList<TableColumn> cols = usernameTable.getColumns();
        cols.get(0).setCellValueFactory(new PropertyValueFactory<User, String>("username"));
    }


    private void updateExistingPromo() {
       //update nakonid felan.TODO
    }

    private void addAUser() {
        Promo promo = (Promo) promoTable.getSelectionModel().getSelectedItem();
        if (promo == null) {
            errorLabel.setText("No promo is selected");
        } else if (usernameText.getText().isBlank()) {
            errorLabel.setText("Enter a name");
        } else {
            try {
                controller.addCustomer(promo.getId(), showController.getUserByName(usernameText.getText(), Constants.manager.getToken()
                ).getId(), Constants.manager.getToken());
                updateUsernameTable(promo);
            } catch (NoAccessException e) {
                errorLabel.setText(e.getMessage());
            } catch (InvalidIdException e) {
                errorLabel.setText(e.getMessage());
            } catch (ObjectAlreadyExistException e) {
                errorLabel.setText(e.getMessage());
            } catch (InvalidTokenException e) {
                errorLabel.setText(e.getMessage());
            } catch (NotLoggedINException e) {
                errorLabel.setText(e.getMessage());
            } catch (NotCustomerException e) {
                errorLabel.setText(e.getMessage());
            }
        }

    }

    private void updateUsernameTable(Promo promo) {
        ObservableList<User> customers = FXCollections.observableList(promo.getCustomers());
        usernameTable.setItems(customers);
        ObservableList<TableColumn> cols = usernameTable.getColumns();
        cols.get(0).setCellValueFactory(new PropertyValueFactory<User, String>("username"));
    }

    private void refreshTable() throws NotLoggedINException, NoAccessException, InvalidTokenException {
        ObservableList list;
        allPromos = controller.getAllPromoCodeForCustomer(null, true, 0, 0, Constants.manager.getToken());
        list = FXCollections.observableList(allPromos);
        promoTable.setItems(list);
        ObservableList<TableColumn> cols = promoTable.getColumns();
        cols.get(0).setCellValueFactory(new PropertyValueFactory<Promo, Date>("startDate"));
        cols.get(1).setCellValueFactory(new PropertyValueFactory<Promo, Date>("endDate"));
        cols.get(2).setCellValueFactory(new PropertyValueFactory<Promo, String>("promoCode"));
        cols.get(3).setCellValueFactory(new PropertyValueFactory<Promo, Double>("percent"));
        cols.get(4).setCellValueFactory(new PropertyValueFactory<Promo, Long>("maxDiscount"));
    }

    public void load() {

    }

    private void add() {
        Date start = Constants.manager.getDateFromDatePicker(startDate);
        Date end = Constants.manager.getDateFromDatePicker(endDate);
        String code = codeText.getText();
        if (!end.after(start)) {
            errorLabel.setText("Wrong date");
            return;
        } else if (code.isBlank() || offPercent.getText().isBlank()) {
            errorLabel.setText("Something's wrong. I can feel it.");
            return;
        }
        try {
            Promo promoCode = new Promo(code, null);
            promoCode.setStartDate(start);
            promoCode.setEndDate(end);
            promoCode.setPercent(Double.parseDouble(offPercent.getText()));
            promoCode.setMaxDiscount(Long.parseLong(maximumOff.getText()));
            controller.createPromoCode(promoCode, Constants.manager.getToken());
            refreshTable();
        } catch (ObjectAlreadyExistException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (NotLoggedINException e) {
            e.printStackTrace();
        }
    }


}
