package Server.controller.chat;

import model.Message;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.DestinationVariable;

@Controller
public class ChatController {
    @MessageMapping("/chat/{receiver}")
    @SendTo("/topic/messages")
    public Message send(@DestinationVariable("receiver") String receiver, Message message) throws Exception {
        return message;
    }
}
