package model.repository.fake;

import model.Comment;
import model.repository.CommentRepository;

import java.util.List;

public class FakeCommentRepository implements CommentRepository {

    public FakeCommentRepository() {

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
