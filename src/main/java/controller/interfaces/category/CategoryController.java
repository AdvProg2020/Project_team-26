package controller.interfaces.category;

public interface CategoryController {

    void addCategory(String name, int parentId, String token);

    void removeACategory(int id, String token);
}
