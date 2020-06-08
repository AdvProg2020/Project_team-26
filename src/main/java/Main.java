import repository.RepositoryContainer;
import view.ControllerContainer;
import view.ViewManager;

public class Main {

    public static void main(String[] args) {
        RepositoryContainer repositoryContainer = new RepositoryContainer("sql");
        ControllerContainer controllerContainer = new ControllerContainer(repositoryContainer);
        ViewManager manager = new ViewManager(controllerContainer);
        manager.startProgram();
    }
}
