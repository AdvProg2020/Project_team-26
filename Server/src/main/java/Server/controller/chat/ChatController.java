package Server.controller.chat;

import exception.InvalidTokenException;
import exception.NoAccessException;
import model.Message;

import model.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {
    @MessageMapping("/chat/{receiver}")
    @SendTo("/topic/messages")
    public Message send(@DestinationVariable("receiver") String receiver, Message message) throws Exception {
        return message;
    }

    @GetMapping("/controller/method/get-online-support/{token}")
    public List<User> getOnlineSupporter(@PathVariable("token") String token) {
        return new ArrayList<>();

    }

    @GetMapping("/controller/method/get-online-users/{token}")
    public List<User> getAllOnlineUser(@PathVariable("token") String token) throws NoAccessException, InvalidTokenException {
        return new ArrayList<>();

    }
}
