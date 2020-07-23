package client.gui.admin;

import client.connectionController.interfaces.account.*;
import client.connectionController.interfaces.category.ICategoryController;
import client.exception.*;
import client.gui.Constants;
import client.gui.customer.OrderTableController;
import client.gui.interfaces.*;
import client.model.*;
import client.ControllerContainer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdminOptionMenuController implements InitializableController {

    @FXML
    private Button promoButton;
    @FXML
    private Button categoryButton;
    @FXML
    private Button usersButton;
    @FXML
    private Button newManagerButton;
    @FXML
    private Button requestsButton;
    @FXML
    private Button commentButton;
    @FXML
    private Button supportButton;
    @FXML
    private Button personalPage;
    @FXML
    private Button orderButton;
    @FXML
    private Button onlineUsersButton;
    @FXML
    private HBox hbox;


    private int userId;
    private User admin;
    private IShowUserController showUserController;
    private ICategoryController categoryController;


    @Override
    public void initialize(int id) throws IOException, InvalidTokenException, NoAccessException {
        showUserController = (IShowUserController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.ShowUserController);
        categoryController = (ICategoryController) Constants.manager.getControllerContainer().getController(ControllerContainer.Controller.CategoryController);
        this.userId = id;
        this.admin = (User) showUserController.getUserById(id, Constants.manager.getToken());
        categoryButton.setOnMouseClicked(e -> {
            try {
                handleCategory();
            } catch (IOException | InvalidIdException ioException) {
                ioException.printStackTrace();
            } catch (InvalidTokenException invalidTokenException) {
                invalidTokenException.printStackTrace();
            } catch (NoAccessException noAccessException) {
                noAccessException.printStackTrace();
            }
        });
        promoButton.setOnMouseClicked(e -> {
            try {
                handlePromo();
            } catch (InvalidTokenException invalidTokenException) {
                invalidTokenException.printStackTrace();
            } catch (NoAccessException | IOException noAccessException) {
                noAccessException.printStackTrace();
            }
        });
        personalPage.setOnMouseClicked(e -> {
            try {
                handlePersonalPage();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        requestsButton.setOnMouseClicked(e -> {
            try {
                handleRequest();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (InvalidTokenException invalidTokenException) {
                invalidTokenException.printStackTrace();
            } catch (NoAccessException noAccessException) {
                noAccessException.printStackTrace();
            } catch (NotLoggedINException notLoggedINException) {
                notLoggedINException.printStackTrace();
            }
        });

        newManagerButton.setOnMouseClicked(e -> {
            try {
                newManagerRegistry();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        usersButton.setOnMouseClicked(e -> {
            try {
                handleUser();
            } catch (NoAccessException noAccessException) {
                noAccessException.printStackTrace();
            } catch (InvalidTokenException invalidTokenException) {
                invalidTokenException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        commentButton.setOnMouseClicked(e -> {
            try {
                handleComments();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (NoAccessException noAccessException) {
                noAccessException.printStackTrace();
            } catch (InvalidTokenException invalidTokenException) {
                invalidTokenException.printStackTrace();
            } catch (InvalidIdException invalidIdException) {
                invalidIdException.printStackTrace();
            }
        });

        supportButton.setOnMouseClicked(e -> {
            try {
                handleSupportRegistry();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (InvalidIdException invalidIdException) {
                invalidIdException.printStackTrace();
            } catch (InvalidTokenException invalidTokenException) {
                invalidTokenException.printStackTrace();
            } catch (NoAccessException noAccessException) {
                noAccessException.printStackTrace();
            }
        });

        orderButton.setOnMouseClicked(e -> {
            try {
                try {
                    handleOrderTable();
                } catch (InvalidIdException invalidIdException) {
                    invalidIdException.printStackTrace();
                } catch (InvalidTokenException invalidTokenException) {
                    invalidTokenException.printStackTrace();
                } catch (NoAccessException noAccessException) {
                    noAccessException.printStackTrace();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        onlineUsersButton.setOnMouseClicked(e -> {
            try {
                handleOnlineUsers();
            } catch (NoAccessException ex) {
                ex.printStackTrace();
            } catch (InvalidTokenException ex) {
                ex.printStackTrace();
            }
        });

        handlePersonalPage();
    }

    private void handleOnlineUsers() throws NoAccessException, InvalidTokenException {
        List<User> onlineUsers = showUserController.getAllOnlineUser(Constants.manager.getToken());
        Stage tableStage = new Stage();
        TableView<User> tableView = new TableView();
        tableStage.setTitle("users");
        TableColumn<User, String> column = new TableColumn("Online");
        column.setCellValueFactory(new PropertyValueFactory<>("username"));
        column.setMinWidth(200);
        tableView.getColumns().addAll(column);
        tableView.setItems(FXCollections.observableList(onlineUsers));
        VBox box = new VBox();
        box.setPadding(new Insets(10, 10, 10, 10));
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(tableView);
        Scene scene = new Scene(box);
        tableStage.setScene(scene);
        tableStage.show();
    }

    private void handleOrderTable() throws IOException, InvalidIdException, InvalidTokenException, NoAccessException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/OrderTableForManager.fxml"));
        Node node = loader.load();
        ManagerOrderController managerOrderController = loader.getController();
        managerOrderController.initialize(0);
        hbox.getChildren().removeAll(hbox.getChildren());
        hbox.getChildren().addAll(node);
    }

    private void handleSupportRegistry() throws IOException, InvalidIdException, InvalidTokenException, NoAccessException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/SupportRegistry.fxml"));
        Node node = loader.load();
        SupportRegistryController supportRegistryController = loader.getController();
        supportRegistryController.initialize(0);
        hbox.getChildren().removeAll(hbox.getChildren());
        hbox.getChildren().addAll(node);
    }

    private void handleComments() throws IOException, NoAccessException, InvalidTokenException, InvalidIdException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ManagerComment.fxml"));
        Node node = loader.load();
        ManagerCommentController managerCommentController = loader.getController();
        managerCommentController.initialize(2);
        hbox.getChildren().removeAll(hbox.getChildren());
        hbox.getChildren().addAll(node);
    }

    private void newManagerRegistry() throws IOException {
        Constants.manager.showAdminRegistryMenu();
    }

    private void handleRequest() throws NoAccessException, InvalidTokenException, IOException, NotLoggedINException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ManagerRequest.fxml"));
        Node node = loader.load();
        ManagerRequestController managerRequestController = loader.getController();
        managerRequestController.initialize(2);
        managerRequestController.load();
        hbox.getChildren().removeAll(hbox.getChildren());
        hbox.getChildren().addAll(node);
    }

    private void handlePersonalPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/AdminMenu.fxml"));
        Node node = loader.load();
        AdminMenuController adminMenuController = loader.getController();
        adminMenuController.initialize(admin.getId());
        adminMenuController.load();
        hbox.getChildren().removeAll(hbox.getChildren());
        hbox.getChildren().addAll(node);
    }

    private void handleCategory() throws IOException, InvalidIdException, NoAccessException, InvalidTokenException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/CategoryAdminOption.fxml"));
        Node node = loader.load();
        CategoryOptionController categoryOptionController = loader.getController();
        categoryOptionController.initialize(1);
        hbox.getChildren().removeAll(hbox.getChildren());
        hbox.getChildren().addAll(node);
    }

    private void handlePromo() throws NoAccessException, InvalidTokenException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/PromoManager.fxml"));
        Node node = loader.load();
        PromoManagerController promoManagerController = (PromoManagerController) loader.getController();
        promoManagerController.initialize(1);
        promoManagerController.load();
        hbox.getChildren().removeAll(hbox.getChildren());
        hbox.getChildren().addAll(node);
    }

    private void handleUser() throws NoAccessException, InvalidTokenException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/ManageUsers.fxml"));
        Node node = loader.load();
        ManageUsersController manageUsersController = loader.getController();
        manageUsersController.initialize(2);
        manageUsersController.load();
        hbox.getChildren().removeAll(hbox.getChildren());
        hbox.getChildren().addAll(node);
    }
}
