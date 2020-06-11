package view.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import model.Comment;
import model.Product;

import java.io.IOException;
import java.util.List;

public class CommentController {

    @FXML
    private VBox commentBox;

    private Product product;

    public CommentController() {
    }

    public void load(Product product) throws IOException {
        commentBox.getChildren().removeAll();
        this.product = product;
        for (Comment comment: product.getComments()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/SingleComment.fxml"));
            SingleCommentController singleCommentController = (SingleCommentController) loader.getController();
            singleCommentController.load(comment);
            commentBox.getChildren().add(loader.load());
        }
    }
}
