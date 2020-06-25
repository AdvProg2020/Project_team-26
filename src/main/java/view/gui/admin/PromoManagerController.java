package view.gui.admin;

import controller.RequestController;
import controller.interfaces.discount.IPromoController;
import controller.interfaces.request.IRequestController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import exception.ObjectAlreadyExistException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.Promo;
import view.cli.ControllerContainer;
import view.gui.Constants;
import view.gui.interfaces.*;
import java.time.LocalDate;
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

    private List<Promo> allPromos = new ArrayList<>();
    private IPromoController controller;


    public void initialize(int id) throws InvalidTokenException, NoAccessException {
        ObservableList list;
        controller = (IPromoController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.PromoController);
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
        ObservableList<Customer> customers = FXCollections.observableList(promo.getCustomers());
        usernameTable.setItems(customers);
        ObservableList<TableColumn> cols = usernameTable.getColumns();
        cols.get(0).setCellValueFactory(new PropertyValueFactory<Customer,String>("username"));
    }


    private void updateExistingPromo() {
    }

    private void addAUser() {
    }

    private void refreshTable() throws NotLoggedINException, NoAccessException, InvalidTokenException {
        ObservableList list;
        allPromos = controller.getAllPromoCodeForCustomer(null,true,0,0, Constants.manager.getToken());
        list = FXCollections.observableList(allPromos);
        promoTable.setItems(list);
        ObservableList<TableColumn> cols = promoTable.getColumns();
        cols.get(0).setCellValueFactory(new PropertyValueFactory<Promo, Date>("startDate"));
        cols.get(1).setCellValueFactory(new PropertyValueFactory<Promo,Date>("endDate"));
        cols.get(2).setCellValueFactory(new PropertyValueFactory<Promo,String>("promoCode"));
        cols.get(3).setCellValueFactory(new PropertyValueFactory<Promo,Double>("percent"));
        cols.get(4).setCellValueFactory(new PropertyValueFactory<Promo,Long>("maxDiscount"));
    }

    public void load() {

    }

    private void add() {
        Date start = Constants.manager.getDateFromDatePicker(startDate);
        Date end = Constants.manager.getDateFromDatePicker(endDate);
        String code = codeText.getText();
        double percent = Double.parseDouble(offPercent.getText());
        if(!end.after(start)) {
            errorLabel.setText("Wrong date");
        } else if (code.isBlank() || offPercent.getText().isBlank()) {
            errorLabel.setText("Something's wrong. I can feel it.");
        }
        try {
            Promo promoCode = new Promo(code,null);
            promoCode.setStartDate(start);
            promoCode.setEndDate(end);
            controller.createPromoCode(promoCode,Constants.manager.getToken());
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
