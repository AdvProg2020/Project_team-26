package view.gui.comment;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import model.Comment;

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
        hasBoughtCheckBox.setSelected(comment.hasBought());
        descriptionText.setText(comment.getText());
    }
}
