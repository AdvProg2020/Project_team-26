package client.gui;

import client.exception.InvalidIdException;
import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.gui.interfaces.InitializableController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MessageCardController implements InitializableController {
    private int userId;
    @FXML
    private HBox box;
    @FXML
    private TextArea message;


    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException, InvalidIdException {
        this.userId = id;
    }

    public void load(Message receivedMessage) {
        if (receivedMessage.getType() == MessageType.JOIN || receivedMessage.getType() == MessageType.LEAVE) {
            box.setAlignment(Pos.CENTER);
            message.setText(receivedMessage.getSender().getFullName() + (receivedMessage.getType() == MessageType.LEAVE ? "" +
                    " Leaved" : " Joined") + " the chat \n" +
                    "at : " + receivedMessage.getTime().toString());
        } else {
            if (receivedMessage.getSender().getId() == userId) {
                box.setAlignment(Pos.CENTER_RIGHT);
                message.setText("at : " + receivedMessage.getTime().toString() + "\n" + receivedMessage.getContent());
            } else {
                box.setAlignment(Pos.CENTER_LEFT);
                message.setText(receivedMessage.getSender().getFullName() + "\nat : " + receivedMessage.getTime().toString() +
                        "\n" + receivedMessage.getContent());

            }
        }
    }

    public int getUserId() {
        return userId;
    }
}
