package Server.controller.chat;

import exception.InvalidTokenException;
import exception.NoAccessException;
import model.Message;

import model.User;
import model.enums.MessageType;
import model.enums.Role;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import repository.ChatRepository;
@Controller
public class ChatController {
    @MessageMapping("/chat/{receiver}")
    @SendTo("/topic/messages")
    public Message send(@DestinationVariable("receiver") String receiver, Message message) throws Exception {
        if (message.getType() == MessageType.JOIN) {
            if (message.getRole() == Role.SUPPORT) {
                ChatRepository.getInstance().addOnlineSupport(message.getSender());
            } else {
                ChatRepository.getInstance().addOnlineUser(message.getSender());
            }
        } else if (message.getType() == MessageType.LEAVE) {
            if (message.getRole() == Role.SUPPORT) {
                ChatRepository.getInstance().removeOnlineSupport(message.getSender());
            } else {
                ChatRepository.getInstance().removeOnlineUser(message.getSender());
            }
        }
        return message;
    }
}
