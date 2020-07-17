package client.gui.admin;

import client.gui.Constants;
import client.gui.interfaces.InitializableController;
import client.model.Comment;
import client.model.enums.CommentState;
import controller.review.CommentController;
import exception.InvalidIdException;
import exception.InvalidTokenException;
import exception.NoAccessException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.cli.ControllerContainer;

import java.io.IOException;
import java.util.List;

public class ManagerCommentController implements InitializableController {


    @FXML
    private Button confirmButton;
    @FXML
    private Button rejectButton;
    @FXML
    private TableView commentTable;
    @FXML
    private TableColumn idCol;
    @FXML
    private TableColumn messageCol;
    @FXML
    private TableColumn statusCol;
    @FXML
    private Label errorLabel;

    private CommentController controller;

    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException, InvalidIdException {
        controller = (CommentController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.CommentController);
        refreshTable();

        confirmButton.setOnMouseClicked(e -> {
            try {
                confirmComment();
            } catch (NoAccessException noAccessException) {
                noAccessException.printStackTrace();
            } catch (InvalidTokenException invalidTokenException) {
                invalidTokenException.printStackTrace();
            }
        });

        rejectButton.setOnMouseClicked(e -> {
            try {
                rejectComment();
            } catch (NoAccessException noAccessException) {
                noAccessException.printStackTrace();
            } catch (InvalidTokenException invalidTokenException) {
                invalidTokenException.printStackTrace();
            }
        });
    }

    private void rejectComment() throws NoAccessException, InvalidTokenException {
        Comment comment = (Comment) commentTable.getSelectionModel().getSelectedItem();
        if(comment == null) {
            errorLabel.setText("Please select a comment.");
        } else {
            controller.rejectComment(comment.getId(), Constants.manager.getToken());
            refreshTable();
        }
    }

    private void refreshTable() throws NoAccessException, InvalidTokenException {
        List<Comment> comments = controller.getAllComments(Constants.manager.getToken());
        commentTable.setItems(FXCollections.observableList(comments));
        idCol.setCellValueFactory(new PropertyValueFactory<Comment,Integer>("id"));
        messageCol.setCellValueFactory(new PropertyValueFactory<Comment,String>("text"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Comment, CommentState>("state"));
    }

    private void confirmComment() throws NoAccessException, InvalidTokenException {
        Comment comment = (Comment) commentTable.getSelectionModel().getSelectedItem();
        if(comment == null) {
            errorLabel.setText("Please select a comment.");
        } else {
            controller.confirmComment(comment.getId(), Constants.manager.getToken());
            refreshTable();
        }
    }
}
