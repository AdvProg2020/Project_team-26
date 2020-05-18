import exception.AlreadyLoggedInException;
import repository.Repository;
import repository.RepositoryContainer;
import repository.mysql.MySQLRepository;
import view.ControllerContainer;
import view.ViewManager;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        RepositoryContainer repositoryContainer = new RepositoryContainer("sql");
        ControllerContainer controllerContainer =  new ControllerContainer(repositoryContainer);
        ViewManager manager = new ViewManager(controllerContainer);
        manager.startProgram();
    }
}
