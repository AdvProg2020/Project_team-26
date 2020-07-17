package client.gui.seller;

import client.gui.Constants;
import client.gui.PersonalInfoController;
import client.gui.interfaces.InitializableController;
import controller.interfaces.discount.IOffController;
import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Off;
import model.enums.Status;
import view.cli.ControllerContainer;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class OffTableController implements InitializableController {
    private PersonalInfoController personalInfoController;
    private IOffController offController;
    private int userId;
    @FXML
    private TableView offTableView;
    @FXML
    private TableColumn<Date, Off> startDate;
    @FXML
    private TableColumn<Date, Off> endDate;
    @FXML
    private TableColumn<Status, Off> status;

    @Override
    public void initialize(int id) throws IOException {
        offController = (IOffController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.OffController);
        this.userId = id;
    }

    private void loadSingleOff(Off off) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/OffPageForSeller.fxml"));
        Node node = loader.load();
        OffControllerPage offControllerPage = (OffControllerPage) loader.getController();
        offControllerPage.initialize(off.getId());
        offControllerPage.loadOffPage(off, this);
        personalInfoController.updateAllBoxWithSingleNode(node);
    }

    public void load(List<Off> offList, PersonalInfoController personalInfoController) {
        this.personalInfoController = personalInfoController;
        loadColumn(FXCollections.observableList(offList));
    }

    private void loadColumn(ObservableList<Off> observableList) {
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        offTableView.setOnMouseClicked(e -> {
            ObservableList<Off> objects = offTableView.getSelectionModel().getSelectedItems();
            if (objects == null || objects.size() == 0)
                return;
            try {
                loadSingleOff(objects.get(objects.size() - 1));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        if (observableList == null)
            return;
        if (observableList.size() == 0)
            return;
        offTableView.setItems(observableList);
    }


    public void deleteOffAndReloadBox(int offId) throws IOException {
        personalInfoController.clearBox();
        try {
            offController.removeAOff(offId, Constants.manager.getToken());
            reloadTable();
        } catch (NoAccessException | InvalidIdException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        } catch (InvalidTokenException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
            Constants.manager.setTokenFromController();
        } catch (NotLoggedINException e) {
            Constants.manager.showLoginMenu();
        }


    }

    public void reloadTable() throws IOException {
        List<Off> offs = null;
        try {
            offs = offController.getAllOfForSellerWithFilter("date", true, 0, 100, Constants.manager.getToken());
            load(offs, this.personalInfoController);
        } catch (NoAccessException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
        } catch (InvalidTokenException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
            Constants.manager.setTokenFromController();
        } catch (NotLoggedINException e) {
            Constants.manager.showLoginMenu();
        }

    }
}
