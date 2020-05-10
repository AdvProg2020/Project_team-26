package model.repository;

import model.repository.fake.FakeCategoryRepository;
import model.repository.fake.FakeCommentRepository;
import model.repository.fake.FakeProductRepository;
import model.repository.fake.FakeUserRepository;

import java.util.HashMap;
import java.util.Map;

public class RepositoryContainer {

    private Map<String, Repository> map;
    public static int idProducer = 5;

    public RepositoryContainer() {
        map = new HashMap<>();
        initialize();
    }

    public void initialize() {
        map.put("ProductRepository", new FakeProductRepository());
        map.put("CategoryRepository", new FakeCategoryRepository());
        map.put("UserRepository", new FakeUserRepository());
        map.put("CommentRepository", new FakeCommentRepository());
    }

    public Repository<?> getRepository(String repositoryName) {
        return map.get(repositoryName);
    }


}
