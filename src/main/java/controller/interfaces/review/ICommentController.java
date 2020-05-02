package controller.interfaces.review;

import exception.NoAccessException;

public interface ICommentController {

    void addAComment(String comment, int productId, String token) throws NoAccessException;

    void removeComment(int id, String token) throws NoAccessException;
}
