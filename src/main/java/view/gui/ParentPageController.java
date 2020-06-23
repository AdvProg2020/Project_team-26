package view.gui;

import java.io.IOException;

public interface ParentPageController {
    void reloadItems() throws IOException;
    void reloadItems(Object object) throws IOException;
}
