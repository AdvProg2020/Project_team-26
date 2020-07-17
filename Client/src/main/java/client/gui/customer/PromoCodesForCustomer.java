package client.gui.customer;

import client.gui.interfaces.InitializableController;
import client.model.Promo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class PromoCodesForCustomer implements InitializableController {
    private int id;
    @FXML
    private TableView tableView;

    @Override
    public void initialize(int id) throws IOException {
        this.id = id;

    }

    public void load(List<Promo> promoList) {
        tableView.setItems(FXCollections.observableList(promoList));
        ObservableList<TableColumn> cols = tableView.getColumns();
        cols.get(0).setCellValueFactory(new PropertyValueFactory<Promo, Date>("startDate"));
        cols.get(1).setCellValueFactory(new PropertyValueFactory<Promo, Date>("endDate"));
        cols.get(2).setCellValueFactory(new PropertyValueFactory<Promo, String>("promoCode"));
        cols.get(3).setCellValueFactory(new PropertyValueFactory<Promo, Double>("percent"));
        cols.get(4).setCellValueFactory(new PropertyValueFactory<Promo, Long>("maxDiscount"));
    }
}
