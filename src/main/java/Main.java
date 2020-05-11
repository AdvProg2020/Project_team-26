import exception.AlreadyLoggedInException;
import view.ViewManager;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws AlreadyLoggedInException {
        ViewManager manager = new ViewManager();
        manager.startProgram();
    }
}
