package client.controller.interfaces.review;

import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.NoObjectIdException;
import model.Comment;

import java.util.List;

public interface ICommentController {

    void addComment(String comment, String title, int productId, String token) throws NoAccessException, InvalidTokenException;

    void removeComment(int id, String token) throws NoAccessException, InvalidTokenException, NoObjectIdException;

    List<Comment> getConfirmedComments(int productId, String token);
}
