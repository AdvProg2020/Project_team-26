package view.gui;

import controller.RequestController;
import controller.interfaces.discount.IPromoController;
import controller.interfaces.request.IRequestController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Promo;
import view.cli.ControllerContainer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PromoManagerController {


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

    private List<Promo> allPromos = new ArrayList<>();
    private IPromoController controller;


    private void load() throws InvalidTokenException, NoAccessException, NotLoggedINException {
        ObservableList list;
        controller = (IPromoController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.PromoController);
        allPromos = controller.getAllPromoCodeForCustomer(null,true,0,0,Constants.manager.getToken());
        list = FXCollections.observableList(allPromos);
        promoTable.setItems(list);
        ObservableList<TableColumn> cols = promoTable.getColumns();
        cols.get(0).setCellValueFactory(new PropertyValueFactory<Promo, Date>("startDate"));
        cols.get(1).setCellValueFactory(new PropertyValueFactory<Promo,Date>("endDate"));
        cols.get(2).setCellValueFactory(new PropertyValueFactory<Promo,String>("promoCode"));
        cols.get(3).setCellValueFactory(new PropertyValueFactory<Promo,Double>("percent"));
        cols.get(4).setCellValueFactory(new PropertyValueFactory<Promo,Long>("maxDiscount"));
    }


}
