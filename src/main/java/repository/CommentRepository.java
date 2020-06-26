package repository;

import model.Comment;

import java.util.List;

public interface CommentRepository extends Repository<Comment> {
    List<Comment> getConfirmedComments(int productId, Pageable pageable); //todo
}
