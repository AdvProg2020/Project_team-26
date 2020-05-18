package repository.mysql;

import model.Comment;
import model.Order;
import repository.CommentRepository;
import repository.OrderRepository;

public class MySQLCommentRepository extends MySQLRepository<Comment> implements CommentRepository {
    public MySQLCommentRepository() {
        super(Comment.class);
    }
}
