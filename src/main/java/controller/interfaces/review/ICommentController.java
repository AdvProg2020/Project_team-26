package controller.interfaces.review;

import exception.InvalidTokenException;
import exception.NoAccessException;

public interface ICommentController {

    void addAComment(String comment, int productId, String token) throws NoAccessException, InvalidTokenException;

    void removeComment(int id, String token) throws NoAccessException, InvalidTokenException;
}
