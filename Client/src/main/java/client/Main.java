package client;


import client.connectionController.account.AuthenticationController;
import client.gui.Constants;
import client.gui.authentication.FirstAdminRegister;
import client.model.Message;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class Main extends Application {
    private static Scene scene;
    private static Stage windowOfApp;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Constants.manager.setTokenFromController();
        if (!((AuthenticationController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.AuthenticationController)).doWeHaveAManager()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/FirstAdminRegister.fxml"));
            Node node = loader.load();
            FirstAdminRegister controller = loader.getController();
            controller.initialize(2);
            controller.load(primaryStage);
            primaryStage.setScene(new Scene((Parent) node));
            primaryStage.setResizable(false);
            primaryStage.show();
        } else {
            Constants.manager.start(primaryStage);
            Constants.manager.openPage("AllProducts", 0);
        }
    }


    static public class MyStompSessionHandler extends StompSessionHandlerAdapter {
        private String userId;

        public MyStompSessionHandler(String userId) {
            this.userId = userId;
        }

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

        private void sendJsonMessage(StompSession session, Message message) {
            session.send("/app/chat/"+message.getReceiver(), message);
        }

        private void subscribeTopic(String topic, StompSession session) {
            session.subscribe(topic, new StompFrameHandler() {

                @Override
                public Type getPayloadType(StompHeaders headers) {
                    return Message.class;
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload) {
                    System.err.println(payload.toString());
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

    public static void main(String[] args) throws Exception {

        ControllerContainer controllerContainer = new ControllerContainer();
        Constants.manager.setControllerContainer(controllerContainer);
       // Constants.manager.startWebSocket();
        launch(args);
    }
}
