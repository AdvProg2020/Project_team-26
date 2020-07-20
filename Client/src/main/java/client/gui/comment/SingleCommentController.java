package client.gui.comment;

import client.model.Comment;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;

public class SingleCommentController {

    @FXML
    private Text titleText;
    @FXML
    private Text buyerNameText;
    @FXML
    private CheckBox hasBoughtCheckBox;
    @FXML
    private Text descriptionText;

    private Comment comment;

    public void load(Comment comment) {
        titleText.setText(comment.getTitle());
        buyerNameText.setText(comment.getCustomer().getFullName());
        hasBoughtCheckBox.setSelected(comment.getHasBought());
        descriptionText.setText(comment.getText());
    }
}
