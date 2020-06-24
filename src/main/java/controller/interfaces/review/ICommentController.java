package controller.interfaces.review;

import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NoObjectIdException;

public interface ICommentController {

    void addComment(String comment, String title , int productId, String token) throws NoAccessException, InvalidTokenException;

    void removeComment(int id, String token) throws NoAccessException, InvalidTokenException, NoObjectIdException;
}
