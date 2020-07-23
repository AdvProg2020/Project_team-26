package client;

import client.gui.Constants;
import client.model.Message;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {
    private void showHeaders(StompHeaders headers) {
        for (Map.Entry<String, List<String>> e : headers.entrySet()) {
            System.err.print("  " + e.getKey() + ": ");
            boolean first = true;
            for (String v : e.getValue()) {
                if (!first) System.err.print(", ");
                System.err.print(v);
                first = false;
            }
            System.err.println();
        }
    }

    private void subscribeTopic(String topic, StompSession session) {
        session.subscribe(topic, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Message.class;
            }
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                Message message = (Message) payload;
                System.out.println(message);
                Constants.manager.getMessageReceivers().forEach(i -> {
                    try {
                        i.received(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.err.println("Connected! Headers:");
        showHeaders(connectedHeaders);
        subscribeTopic("/topic/messages", session);
    }
}
