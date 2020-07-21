package client.gui;

import client.exception.InvalidIdException;
import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.gui.interfaces.InitializableController;
import client.model.Message;
import client.model.enums.MessageType;
import client.model.enums.Role;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ChatPageController implements InitializableController {
    private int userId;
    private String receiver;
    private String sender;
    private Role role;
    @FXML
    private ScrollPane chatScroll;
    @FXML
    private VBox chatVBox;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private ImageView sendImage;

    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException, InvalidIdException {
        userId = id;
        sendImage.setOnMouseClicked(e -> {
            sendMessage(MessageType.CHAT);
        });
    }

    public void load(String sender, String receiver) {
        this.receiver = receiver;
        this.sender = sender;
    }

    public void sendMessage(MessageType type) {
        if (messageTextArea.getText().isEmpty() || messageTextArea.getText().isBlank())
            return;
        Message message = new Message(sender, messageTextArea.getText(), receiver, type, this.role);
        Constants.manager.sendMessageTOWebSocket(Constants.manager.getSession(), this.receiver, message);
        addMessageToBox(message);
    }

    private void addMessageToBox(Message message) {


    }


}
