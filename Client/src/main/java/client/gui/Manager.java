package client.gui;

import client.ControllerContainer;
import client.connectionController.SessionController;
import client.connectionController.account.AuthenticationController;
import client.connectionController.interfaces.account.IShowUserController;
import client.exception.InvalidIdException;
import client.exception.InvalidTokenException;
import client.exception.NoAccessException;
import client.exception.NotLoggedINException;
import client.gui.admin.AdminRegistryController;
import client.gui.authentication.AuthenticationStageManager;
import client.gui.authentication.RegisterMenuController;
import client.gui.interfaces.InitializableController;
import client.gui.interfaces.MessageReceiver;
import client.gui.interfaces.Reloadable;
import client.model.Message;
import client.model.User;
import client.model.enums.MessageType;
import client.model.enums.Role;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.io.IOException;
import java.time.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Manager implements Reloadable {

    private ControllerContainer controllerContainer;
    private String token;
    private User loggedInUser;
    private boolean isLoggedIn;
    private Role role;
    private List<Pair<String, Integer>> pages;
    private MainController controller;
    private AuthenticationStageManager authenticationStageManager;
    private Set<Integer> compareList;
    private Stage popUp;
    private final String hostPort = "http://localhost:8080";
    public final String chatUrl = "ws://localhost:8080/chat";
    private StompSession session;
    private List<MessageReceiver> messageReceivers;

    public Manager() {
        pages = new ArrayList<>();
        compareList = new HashSet<>();
        messageReceivers = new ArrayList<>();
    }

    public void setSession(StompSession session) {
        this.session = session;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }


    public String getChatUrl() {
        return chatUrl;
    }

    public StompSession getSession() {
        return Constants.manager.session;
    }

    public String getHostPort() {
        return hostPort;
    }

    public void openPage(String pageName, int id) throws IOException {
        pages.add(new Pair<>(pageName, id));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/" + pageName + ".fxml"));
        Parent parent = loader.load();
        InitializableController controller = loader.getController();
        showNode(parent);
        try {
            controller.initialize(id);//TODO handle kon
            reloadTop();
        } catch (InvalidTokenException e) {
            Constants.manager.setTokenFromController();
            Constants.manager.showErrorPopUp("Your token was invalid.");
        } catch (NoAccessException e) {
            Constants.manager.openPage("AllProducts", 0);
        } catch (InvalidIdException e) {
            Constants.manager.showErrorPopUp(e.getMessage());
            Constants.manager.openPage("AllProducts", 0);
        }
    }

    public void postRequestWithVoidReturnType(JSONObject jsonObject, String address) throws HttpClientErrorException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toJSONString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForLocation(address, httpEntity);
    }

    public String getStringValueFromServerByAddress(String address, String token) throws HttpClientErrorException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(address, httpEntity, String.class);
        return responseEntity.getBody();
    }

    public void back() throws IOException {
        if (pages.size() > 1) {
            pages.remove(pages.size() - 1);
            Pair<String, Integer> page = pages.remove(pages.size() - 1);
            openPage(page.getKey(), page.getValue());
        }
    }

    public List<Pair<String, Integer>> getPages() {
        return pages;
    }

    public Set<Integer> getCompareList() {
        return compareList;
    }

    private void showNode(Node node) {
        controller.setCenter(node);
    }

    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/Main.fxml"));
        Scene scene = new Scene(loader.load());
        controller = loader.getController();
        reloadTop();
        primaryStage.setTitle("Store");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void reloadTop() {
        controller.reload();
    }

    public ControllerContainer getControllerContainer() {
        return controllerContainer;
    }

    public void setControllerContainer(ControllerContainer controllerContainer) {
        this.controllerContainer = controllerContainer;
    }

    public <T> T getItemFromServer(JSONObject jsonObject, String address, Class tClass) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(address, httpEntity, tClass);
        return responseEntity.getBody();
    }

    public <T> List<T> getListItemsFromServer(JSONObject jsonObject, String address, Class tClass) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toString(), httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<T[]> responseEntity = restTemplate.postForEntity(address, httpEntity, tClass);
        return Arrays.asList(responseEntity.getBody());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean checkInputIsInt(String input) {
        try {
            if (0 > Integer.parseInt(input))
                return false;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean checkIsLong(String input) {
        try {
            if (0 > Long.parseLong(input))
                return false;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void openAccountPage() throws IOException {
        try {
            User user = ((IShowUserController) controllerContainer.getController(ControllerContainer.Controller.ShowUserController)).getUserByToken(getToken());
            if (user.getRole() == Role.SELLER) {
                openPage("SellerButtons", user.getId());
            } else if (user.getRole() == Role.CUSTOMER) {
                openPage("CustomersButton", user.getId());
            } else if (user.getRole() == Role.ADMIN) {
                openPage("AdminOptionMenu", user.getId());
            } else if (user.getRole() == Role.SUPPORT) {
                openPage("SupporterButtons", user.getId());
            } else {
                //TODO
            }
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }
    }

    public void openCart() throws IOException {
        openPage("Cart", 0);
    }

    public void setTokenFromController() {
        this.token = ((SessionController) controllerContainer.getController(ControllerContainer.Controller.SessionController)).createToken();
    }

    public List<MessageReceiver> getMessageReceivers() {
        return messageReceivers;
    }

    public void showComparePage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ComparePane.fxml"));
        Parent parent = loader.load();
        ComparePane controller = loader.getController();
        Button exit = controller.getExitButton();
        addProductToComparePane(controller);
        Stage windows = new Stage();
        windows.setScene(new Scene(parent));
        windows.setMaxWidth(1000);
        windows.setMaxHeight(600);
        exit.setOnMouseClicked(e -> {
            windows.close();
            compareList = new HashSet<>();
            reloadTop();
        });
        windows.initModality(Modality.APPLICATION_MODAL);
        windows.setResizable(false);
        windows.setOnCloseRequest(e -> {
            compareList = new HashSet<>();
            windows.close();
            reloadTop();
        });
        windows.show();
    }

    private void addProductToComparePane(ComparePane controller) {
        compareList.forEach(i -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/CompareProduct.fxml"));
            try {
                Node node = loader.load();
                CompareController compareController = loader.getController();
                compareController.initialize(i);
                controller.addToBox(node);
            } catch (IOException | InvalidIdException e) {
                e.printStackTrace();//todo
            }
        });
    }

    public void setAuthenticationStageManager(AuthenticationStageManager authenticationStageManager) {
        this.authenticationStageManager = authenticationStageManager;
    }

    public void switchScene(String scene) throws IOException {
        if (scene.equals("Login"))
            authenticationStageManager.switchToLogin();
        else
            authenticationStageManager.switchToRegister();
    }

    public boolean checkIsPercent(String input) {
        String[] split = input.split("%");
        if (checkIsDouble(split[0])) {
            if (Double.parseDouble(split[0]) >= 100) {
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean checkIsDouble(String input) {
        try {
            if (0 > Double.parseDouble(input))
                return false;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Date getDateFromDatePicker(DatePicker datePicker) throws DateTimeException, IllegalArgumentException {
        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        if (date.getTime() < 0)
            throw new IllegalArgumentException("pick closer");
        return date;
    }

    public LocalDate getLocalDateFromDate(Date date) throws DateTimeException, IllegalArgumentException {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate;
    }

    public void showLoginMenu() throws IOException {
        popUp = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/RegisterMenu.fxml"));
        VBox vBox = new VBox((Node) loader.load());
        RegisterMenuController controller = loader.getController();
        controller.initialize(2);
        controller.setReloadable(this);
        popUp.setScene(new Scene(vBox));
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setResizable(false);
        controller.redirectToLogin();
        popUp.show();
    }

    public void showRegisterMenu() throws IOException {
        popUp = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/RegisterMenu.fxml"));
        VBox vBox = new VBox((Node) loader.load());
        RegisterMenuController controller = loader.getController();
        controller.initialize(2);
        controller.setReloadable(this);
        popUp.setScene(new Scene(vBox));
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setResizable(false);
        popUp.show();
    }

    public void showAdminRegistryMenu() throws IOException {
        popUp = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/AdminRegistryMenu.fxml"));
        Node node = loader.load();
        AdminRegistryController controller = loader.getController();
        controller.initialize(2);
        popUp = new Stage();
        popUp.setScene(new Scene((Parent) node));
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setResizable(false);
        popUp.show();
    }

    public void showErrorPopUp(String errorMessage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ErrorPage.fxml"));
        Parent parent = loader.load();
        Stage windows = new Stage();
        windows.setScene(new Scene(parent));
        ErrorPageController controller = loader.getController();
        Button okButton = controller.getButton();
        controller.load(errorMessage);
        okButton.setOnMouseClicked(e -> windows.close());
        windows.initModality(Modality.APPLICATION_MODAL);
        windows.setResizable(false);
        windows.show();
    }

    public void showSuccessPopUp(String message) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/SuccessPage.fxml"));
        Parent parent = loader.load();
        Stage windows = new Stage();
        windows.setScene(new Scene(parent));
        SuccessPageController controller = loader.getController();
        Button okButton = controller.getButton();
        controller.load(message);
        okButton.setOnMouseClicked(e -> windows.close());
        windows.initModality(Modality.APPLICATION_MODAL);
        windows.setResizable(false);
        windows.show();
    }

    public void closePopUp() {
        popUp.close();
    }

    public void addToCompareList(int productId) {
        compareList.add(productId);
    }

    public void logout() throws IOException, InvalidTokenException, NotLoggedINException {
        AuthenticationController controller = (AuthenticationController) controllerContainer.getController(ControllerContainer.Controller.AuthenticationController);
        controller.logout(getToken());
        Constants.manager.sendMessageTOWebSocket("", new Message(loggedInUser.getUsername(), "", "", MessageType.LEAVE, loggedInUser.getRole()));
        setLoggedInUser(null);
        setLoggedIn(false);
        reloadTop();
        openPage("AllProducts", 0);
    }

    @Override
    public void reload() {
        reloadTop();
        Pair<String, Integer> page = pages.remove(pages.size() - 1);
        try {
            openPage(page.getKey(), page.getValue());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void sendMessageTOWebSocket(String receiver, Message message) {
        System.out.println("\n\n\n"+message.toString());
        session.send("/app/chat/" + receiver, message);
    }

    public void showRandomPromoIfUserGet() throws IOException {
     /*   IPromoController promoController = (IPromoController) Constants.manager.getControllerContainer().
                getController(ControllerContainer.Controller.PromoController);
        String promo = null;
        try {
            promo = promoController.get(Constants.manager.getToken());
            if (promo == null)
                return;
            if (promo.equals(""))
                return;
            Constants.manager.showSuccessPopUp("promo: " + promo);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }*/
    }


}
