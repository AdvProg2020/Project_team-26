package repository;

import repository.fake.*;
import repository.fake.*;

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
        map.put("RatingRepository", new FakeRatingRepository());
        map.put("ProductSellerRepository",new FakeProductSellerRepository());
        map.put("PromoRepository",new FakePromoRepository());
    }

    public Repository<?> getRepository(String repositoryName) {
        return map.get(repositoryName);
    }


}
