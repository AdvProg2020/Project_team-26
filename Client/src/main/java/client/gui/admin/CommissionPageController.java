package client.gui.admin;

import client.ControllerContainer;
import client.connectionController.interfaces.session.ISessionController;
import client.exception.InvalidIdException;
import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.io.IOException;

public class CommissionPageController implements InitializableController {
    private int userId;
    private ISessionController sessionController;
    @FXML
    private Button creditButton;
    @FXML
    private Button commissionButton;
    @FXML
    private TextField credit;
    @FXML
    private TextField commission;
    @FXML
    private Label errorLabel;

    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException, InvalidIdException {
        sessionController = (ISessionController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.SessionController);
        this.userId = id;
        commission.setPromptText("" + sessionController.getCommission(Constants.manager.getToken()));
        credit.setPromptText("" + sessionController.getMinCredit(Constants.manager.getToken()));
        creditButton.setOnMouseClicked(e -> {
            creditButtonClicked();
        });
        commissionButton.setOnMouseClicked(e -> {
            commissionButtonClicked();
        });
    }

    public void creditButtonClicked() {
        try {
            long creditAmount = Long.parseLong(credit.getText());
            if (creditAmount < 0) {
                errorLabel.setText("enter positive");
            }
            sessionController.setMinCredit(creditAmount, Constants.manager.getToken());
        } catch (NumberFormatException e) {
            errorLabel.setText("invalid Format");

        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }

    }

    public void commissionButtonClicked() {
        try {
            double commissionAmount = Double.parseDouble(commission.getText());
            if (commissionAmount < 0) {
                errorLabel.setText("enter positive");
            }
            sessionController.setCommission(commissionAmount, Constants.manager.getToken());
        } catch (NumberFormatException e) {
            errorLabel.setText("invalid Format");

        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }
    }


}
