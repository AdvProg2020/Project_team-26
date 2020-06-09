import javafx.application.Application;
import javafx.stage.Stage;
import repository.RepositoryContainer;
import view.ControllerContainer;
import view.ViewManager;

public class Main extends Application {

    public static void main(String[] args) {
        RepositoryContainer repositoryContainer = new RepositoryContainer("sql");
        ControllerContainer controllerContainer = new ControllerContainer(repositoryContainer);
        ViewManager manager = new ViewManager(controllerContainer);
        manager.startProgram();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }
}
