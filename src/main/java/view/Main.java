package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Scene scene;
    private static Stage windowOfApp;

    @Override
    public void start(Stage primaryStage) throws Exception {
        windowOfApp = primaryStage;
        windowOfApp.setResizable(false);
        scene = new Scene(loadFXML("mainMenu"));
        windowOfApp.setScene(scene);
        windowOfApp.setOnCloseRequest(e -> {
            windowOfApp.close();
        });
        windowOfApp.show();

    }

    public  void setRootForScene(String fxml) throws IOException {
        windowOfApp.setResizable(true);
        scene = new Scene(loadFXML(fxml));
        windowOfApp.setResizable(false);
        windowOfApp.setScene(scene);
    }

    private  Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
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
