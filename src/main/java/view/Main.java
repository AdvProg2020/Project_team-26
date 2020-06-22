package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.RepositoryContainer;
import view.cli.ControllerContainer;
import view.gui.Constants;
import view.gui.Manager;

import java.io.IOException;

public class Main extends Application {
    private static Scene scene;
    private static Stage windowOfApp;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Constants.manager.start(primaryStage);
        Constants.manager.openPage("AllProduct", 0);
    }

    public  void setRootForScene(String fxml) throws IOException {
        windowOfApp.setResizable(true);
        scene = new Scene(loadFXML(fxml));
        windowOfApp.setResizable(false);
        windowOfApp.setScene(scene);
    }

    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        RepositoryContainer repositoryContainer = new RepositoryContainer("sql");
        ControllerContainer controllerContainer = new ControllerContainer(repositoryContainer);
        Constants.manager.setControllerContainer(controllerContainer);
        launch(args);
    }

    /* public static void main(String[] args) {
     *//* RepositoryContainer repositoryContainer = new RepositoryContainer("sql");
        ControllerContainer controllerContainer = new ControllerContainer(repositoryContainer);
        ViewManager manager = new ViewManager(controllerContainer);
        manager.startProgram();*//*
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {


    }*/
}
