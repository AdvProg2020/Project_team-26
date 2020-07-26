package client;


import client.connectionController.account.AuthenticationController;
import client.exception.InvalidTokenException;
import client.exception.NotLoggedINException;
import client.gui.Constants;
import client.gui.authentication.FirstAdminRegister;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

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
            primaryStage.setOnCloseRequest(e -> {
                Platform.runLater(() -> {
                    e.consume();
                    System.out.println("quit request unexpected");
                    if (Constants.manager.getLoggedInUser() != null) {
                        try {
                            Constants.manager.logout();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (InvalidTokenException ex) {
                            ex.printStackTrace();
                        } catch (NotLoggedINException ex) {
                            ex.printStackTrace();
                        }
                    }
                    primaryStage.close();
                });
            });
            primaryStage.show();

        } else {
            Constants.manager.start(primaryStage);

            Constants.manager.openPage("AllProducts", 0);
        }
    }

    public static void main(String[] args) throws Exception {

        ControllerContainer controllerContainer = new ControllerContainer();
        Constants.manager.setControllerContainer(controllerContainer);
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
       // startWebSocket();
        launch(args);
    }

    private static void startWebSocket() throws Exception {
        WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(simpleWebSocketClient));
        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        org.springframework.util.concurrent.ListenableFuture<org.springframework.messaging.simp.stomp.StompSession> start = stompClient.connect(Constants.manager.chatUrl, sessionHandler);
        StompSession session = start.get();
        Constants.manager.setSession(session);
    }
}
