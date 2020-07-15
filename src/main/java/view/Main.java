package view;

import controller.account.AuthenticationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.RepositoryContainer;
import view.cli.ControllerContainer;
import view.gui.Constants;
import view.gui.authentication.FirstAdminRegister;

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

    public static void main(String[] args) {
        RepositoryContainer repositoryContainer = new RepositoryContainer("sql");
        ControllerContainer controllerContainer = new ControllerContainer(repositoryContainer);
        Constants.manager.setControllerContainer(controllerContainer);
        launch(args);
    }

    /* public static void main(String[] args) {
        RepositoryContainer repositoryContainer = new RepositoryContainer("sql");
        ControllerContainer controllerContainer = new ControllerContainer(repositoryContainer);
        ViewManager manager = new ViewManager(controllerContainer);
        manager.startProgram();*//*
        launch(args);
    }*/
}
