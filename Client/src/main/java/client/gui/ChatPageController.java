package client.gui;

import client.exception.InvalidIdException;
import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.gui.interfaces.InitializableController;
import client.gui.interfaces.MessageReceiver;
import client.model.Message;
import client.model.enums.MessageType;
import client.model.enums.Role;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ChatPageController implements InitializableController, MessageReceiver {
    private int userId;
    private String receiver;
    private String sender;
    private Role role;
    private boolean isItSupport;
    @FXML
    private ScrollPane chatScroll;
    @FXML
    private VBox chatVBox;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private ImageView sendImage;

    @Override
    public void initialize(int id) throws IOException {
        userId = id;
        sendImage.setOnMouseClicked(e -> {
            sendMessage(MessageType.CHAT);
        });
        Constants.manager.getMessageReceivers().add(this::received);
    }


    public void load(String sender, String receiver, Role role, boolean isItSupport) {
        this.isItSupport = isItSupport;
        this.role = role;
        this.receiver = receiver;
        this.sender = sender;
    }

    public void sendMessage(MessageType type) {
        if (messageTextArea.getText().isEmpty() || messageTextArea.getText().isBlank())
            return;
        Message message = new Message(sender, messageTextArea.getText(), receiver, type, this.role);
        Constants.manager.sendMessageTOWebSocket( this.receiver, message);
    }

    private void addMessageToBox(Message message, String sender, Pos pos) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/MessageCard.fxml"));
        Node node = loader.load();
        MessageCardController messageCardController = (MessageCardController) loader.getController();
        try {
            messageCardController.initialize(userId);
            messageCardController.load(message, sender, pos);
            chatVBox.getChildren().addAll(node);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        } catch (NoAccessException e) {
            e.printStackTrace();
        } catch (InvalidIdException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void received(Message message) throws IOException {
        if (isItSupport) {
            if (message.getReceiver().equals(this.sender)) {
                addMessageToBox(message, message.getSender(), Pos.CENTER_LEFT);
            } else if (message.getSender().equals(sender) && message.getReceiver().equals(receiver)) {
                addMessageToBox(message, "You", Pos.CENTER_RIGHT);
            }
        } else if (message.getReceiver().equals(receiver)) {
            if (message.getReceiver().equals(receiver)) {
                addMessageToBox(message, message.getSender(), Pos.CENTER_LEFT);
            } else if (message.getSender().equals(sender) && message.getReceiver().equals(receiver)) {
                addMessageToBox(message, "You", Pos.CENTER_RIGHT);
            }
        }
    }
}
