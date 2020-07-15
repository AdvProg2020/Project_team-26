package repository.fake;

import model.Comment;
import model.Customer;
import repository.CommentRepository;
import repository.Pageable;
import repository.RepositoryContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FakeCommentRepository implements CommentRepository {

    List<Comment> allComments;
    public  int lastId = 5;
    private RepositoryContainer repositoryContainer;
    private FakeUserRepository fakeUserRepository;
    private FakeProductRepository fakeProductRepository;

    public FakeCommentRepository() {
        fakeUserRepository = new FakeUserRepository();
        fakeProductRepository = new FakeProductRepository();
        this.allComments = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            save(new Comment((Customer)fakeUserRepository.getById(i + 8),fakeProductRepository.getById(i),"Garbage","New Comment"));
        }
    }

    @Override
    public List<Comment> getAll() {
        return allComments;
    }

    @Override
    public List<Comment> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public Comment getById(int id) {
        List<Comment> comments = allComments.stream().filter(Comment -> Comment.getId() == id).collect(Collectors.toList());
        if (comments.size() == 0)
            return null;
        return comments.get(0);
    }

    @Override
    public void save(Comment object) {
        lastId++;
        object.setId(lastId);
        allComments.add(object);
    }

    @Override
    public void delete(int id) {
        allComments.remove(getById(id));
    }

    @Override
    public void delete(Comment object) {
        allComments.remove(object);
    }

    @Override
    public boolean exist(int id) {
        return false;
    }

    @Override
    public List<Comment> getAllSorted(String sortField, boolean isAscending) {
        return null;
    }


    @Override
    public List<Comment> getConfirmedComments(int productId, Pageable pageable) {
        return null;
    }
}
