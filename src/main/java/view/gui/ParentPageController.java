package view.gui;

import java.io.IOException;

public interface ParentPageController {
    void reloadItems();
    void reloadItems(Object object) throws IOException;
}
