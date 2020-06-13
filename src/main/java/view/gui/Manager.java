package view.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.util.Pair;
import view.cli.ControllerContainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Manager {

    private ControllerContainer controllerContainer;
    private String token;
    private boolean isLoggedIn;
    private List<Pair<String, Integer>> pages;

    public Manager() {
        pages = new ArrayList<>();
    }

    public void openPage(String pageName, int id) throws IOException {
        pages.add(new Pair<>(pageName, id));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/" + pageName + ".fxml"));
        InitializableController controller = (InitializableController) loader.getController();
        controller.initialize(id);
        Node node = loader.load();
        showNode(node);
    }

    public void back() throws IOException {
        Pair<String, Integer> page = pages.remove(pages.size() - 1);
        openPage(page.getKey(), page.getValue());
    }

    private void showNode(Node node) {
        // TODO: show the node in the main scene
    }

    public ControllerContainer getControllerContainer() {
        return controllerContainer;
    }

    public void setControllerContainer(ControllerContainer controllerContainer) {
        this.controllerContainer = controllerContainer;
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

    public boolean checkInputIsInt(String input) {
        try {
            Long.parseLong(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
