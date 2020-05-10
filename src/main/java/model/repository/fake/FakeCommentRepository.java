package model.repository.fake;

import model.Category;
import model.Comment;
import model.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

public class FakeCommentRepository implements CommentRepository {

    List<Comment> allComments;
    public static int lastId = 5;

    public FakeCommentRepository() {
        this.allComments = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            save(new Comment(i + 1));
            Comment comment = allComments.get(i);
            comment.
        }
    }

    @Override
    public List<Comment> getAll() {
        return null;
    }

    @Override
    public Comment getById(int id) {
        return null;
    }

    @Override
    public void save(Comment object) {
        lastId++;
        object.setId(lastId);
        allComments.add(object);
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(Comment object) {

    }

    @Override
    public boolean exist(int id) {
        return false;
    }
}
