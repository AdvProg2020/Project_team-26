package view.gui.seller;

import controller.interfaces.discount.IOffController;
import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NotLoggedINException;
import javafx.collections.FXCollections;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.*;
import view.gui.Constants;
import view.gui.InitializableController;
import view.gui.OrderItemController;
import view.gui.PersonalInfoController;

import java.io.IOException;
import java.util.*;

public class OffTableController implements InitializableController {
    private PersonalInfoController personalInfoController;
    private IOffController offController;
    @FXML
    private TableView offTableView;
    @FXML
    private TableColumn<Date,Off> startDate;
    @FXML
    private TableColumn<Date,Off> endDate;
    @FXML
    private TableColumn<Status,Off> status;

    @Override
    public void initialize(int id) throws IOException {

        offTableView.setOnMouseClicked(e->{
            ObservableList<Off> itemsList =  offTableView.getSelectionModel().getSelectedItems();
            try {
                loadSingleOff(itemsList.get(itemsList.size()-1));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    private void loadSingleOff(Off off) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/OffPageForSeller.fxml"));
        OffControllerPage offControllerPage = (OffControllerPage) loader.getController();
        offControllerPage.initialize(off.getId());
        offControllerPage.loadOffPage(off,this);
        personalInfoController.updateAllBox(loader.load());
    }
    public void load(List<Off> offList, PersonalInfoController personalInfoController){
        //todo



    }


    public void deleteOffAndReloadBox(int offId){
        personalInfoController.clearBox();
        try {
            offController.removeAOff(offId, Constants.manager.getToken());
            reloadTable();
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidIdException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        } catch (NotLoggedINException e) {
            e.printStackTrace();
        }


    }
    public void reloadTable(){
        List<Off> offs = null;
        try {
            offs = offController.getAllOfForSellerWithFilter("date",true,0,100, Constants.manager.getToken());
            load(offs,this.personalInfoController);
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        } catch (NotLoggedINException e) {
            e.printStackTrace();
        }

    }
}
