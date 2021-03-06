package client.gui.comment;

import client.ControllerContainer;
import client.connectionController.interfaces.review.ICommentController;
import client.exception.*;
import client.gui.Constants;
import client.model.*;
import client.model.enums.Role;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class CommentController {
    private ICommentController commentController;

    @FXML
    private VBox commentBox;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Button submitButton;

    private Product product;

    public CommentController() {
        commentController = (ICommentController) Constants.manager.getControllerContainer().
                getController(ControllerContainer.Controller.CommentController);
    }

    public void load(Product product) throws IOException {
        commentBox.getChildren().clear();
        this.product = product;
        List<Comment> commentList = null;
        try {
            commentList = commentController.getConfirmedComments(product.getId(), Constants.manager.getToken());

        Separator separator = null;
        for (Comment comment: commentList) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/SingleComment.fxml"));
            commentBox.getChildren().add(loader.load());
            SingleCommentController singleCommentController = (SingleCommentController) loader.getController();
            singleCommentController.load(comment);
            separator = new Separator();
            commentBox.getChildren().add(separator);
        }
        commentBox.getChildren().remove(separator);

        if(!Constants.manager.isLoggedIn() || Constants.manager.getRole() != Role.CUSTOMER) {
            titleField.setEditable(false);
            descriptionField.setEditable(false);
            titleField.setPromptText("Login as customer to enter comment");
            descriptionField.setPromptText("Login as customer to enter comment");
            submitButton.setDisable(true);
        }
        } catch (InvalidIdException e) {
            e.printStackTrace();
        }
    }

    public void submit() throws IOException {
        if(titleField.getText().equals("")) {
            Constants.manager.showErrorPopUp("Please enter title");
        } else if (descriptionField.getText().equals("")) {
            Constants.manager.showErrorPopUp("Please enter description");
        } else {
            try {
                commentController.addComment(descriptionField.getText(), titleField.getText(),
                        product.getId(), Constants.manager.getToken());
            } catch (NoAccessException e) {
                Constants.manager.showErrorPopUp(e.getMessage() + "\nBecause you are not a customer.");
            } catch (InvalidTokenException e) {
                Constants.manager.setTokenFromController();
                Constants.manager.showErrorPopUp("Your token was invalid.");
            }
        }
    }
}
