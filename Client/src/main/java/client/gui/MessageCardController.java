package client.gui;

import client.exception.InvalidIdException;
import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.gui.interfaces.InitializableController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MessageCardController implements InitializableController {
    private int id;
    @FXML
    private HBox box;
    @FXML
    private TextArea message;


    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException, InvalidIdException {
        this.id = id;
    }

    public void load(String sender, String senderMessage, Pos position) {
        box.setAlignment(position);
        if (position == Pos.CENTER_LEFT) {
            message.setText(sender + " : " + "\n" + senderMessage);
        } else {
            message.setText(senderMessage);
        }
    }

    public int getUserId() {
        return id;
    }
}
