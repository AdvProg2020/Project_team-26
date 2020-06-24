package view.gui.comment;

import controller.interfaces.review.ICommentController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Comment;
import model.Product;
import model.enums.Role;
import view.cli.ControllerContainer;
import view.gui.Constants;

import java.io.IOException;

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
        commentBox.getChildren().removeAll();
        this.product = product;
        Separator separator = null;
        for (Comment comment: product.getComments()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/SingleComment.fxml"));
            SingleCommentController singleCommentController = (SingleCommentController) loader.getController();
            singleCommentController.load(comment);
            commentBox.getChildren().add(loader.load());
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
