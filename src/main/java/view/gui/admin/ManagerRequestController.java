package view.gui.admin;

import controller.RequestController;
import controller.account.AuthenticationController;
import controller.interfaces.request.IRequestController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Request;
import view.cli.ControllerContainer;
import view.gui.Constants;
import view.gui.interfaces.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManagerRequestController implements InitializableController{


    @FXML
    private TextArea requestDescription;
    @FXML
    private TableView requestTable;
    @FXML
    private Button acceptButton;
    @FXML
    private Button rejectButton;

    private List<Request> allRequests = new ArrayList<>();
    private IRequestController requestController;

    @FXML
    private void acceptRequest() {

    }

    public void load() throws InvalidTokenException, NoAccessException, NotLoggedINException {
        requestController = (IRequestController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.RequestController);
        allRequests = requestController.getAllRequests(null,true,0,0,Constants.manager.getToken());
        requestTable.setItems(FXCollections.observableList(allRequests));
        ObservableList<TableColumn> cols = requestTable.getColumns();
        cols.get(0).setCellValueFactory(new PropertyValueFactory<Request,Integer>("id"));
        cols.get(1).setCellValueFactory(new PropertyValueFactory<Request,String>("username"));
        cols.get(2).setCellValueFactory(new PropertyValueFactory<Request,String>("requestType"));
    }

    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException {

    }
}
