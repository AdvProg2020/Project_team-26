package view.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Manager {

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
}
