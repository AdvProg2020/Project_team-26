package controller.interfaces.review;

public interface ICommentController {

    void addAComment(String[] comment, int productId, String token);

    void removeComment(int id, String token);
}
