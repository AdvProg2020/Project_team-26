package client.gui;

import client.exception.InvalidIdException;
import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.gui.interfaces.InitializableController;
import client.model.Message;
import client.model.enums.MessageType;
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
        box.setVisible(true);
        message.setVisible(true);
        message.setEditable(false);
        this.userId = id;
    }

    public void load(Message receivedMessage, String sender, Pos pos) {
        if (receivedMessage.getType() == MessageType.JOIN || receivedMessage.getType() == MessageType.LEAVE) {
            box.setAlignment(Pos.CENTER);
            message.setText(receivedMessage.getSender() + (receivedMessage.getType() == MessageType.LEAVE ? "" +
                    " Leaved" : " Joined") + " the chat \n" +
                    "at : " + receivedMessage.getTime().toString());
        } else {
            box.setAlignment(pos);
            message.setText(sender + "\nat : " + receivedMessage.getTime().toString() +
                    "\n" + receivedMessage.getContent());
        }
        System.out.println(receivedMessage);
    }

    public int getUserId() {
        return userId;
    }
}
