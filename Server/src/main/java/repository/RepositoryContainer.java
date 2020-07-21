package repository;

import repository.fake.*;
import repository.mysql.*;

import java.util.HashMap;
import java.util.Map;

public class RepositoryContainer {

    private static RepositoryContainer instance = null;

    private Map<String, Repository> map;
    public static int idProducer = 5;

    public static RepositoryContainer getInstance() {
        if (instance == null) {
            instance = new RepositoryContainer("sql");
        }
        return instance;
    }

    public RepositoryContainer() {
        map = new HashMap<>();
        initializeFakeRep();
    }

    public RepositoryContainer(String sql) {
        map = new HashMap<>();
        if (sql.equals("sql")) {
            MySQLRepository.setRepositoryContainer(this);
            initializeSQLRep();
        }
    }

    public void initializeSQLRep() {
        map.put("RequestRepository", new MySQLRequestRepository());
        map.put("CategoryRepository", new MySQLCategoryRepository());
        map.put("ProductRepository", new MySQLProductRepository());
        map.put("UserRepository", new MySQLUserRepository());
        map.put("CommentRepository", new MySQLCommentRepository());
        map.put("RatingRepository", new MySQLRateRepository());
        map.put("PromoRepository", new MySQLPromoRepository());
        map.put("ProductSellerRepository", new MySQLProductSellerRepository());
        map.put("OffRepository", new MySQLOffRepository());
        map.put("OrderRepository", new MySQLOrderRepository());
        map.put("CustomerRepository", new MySQLCustomerRepository());
        map.put("AuctionRepository", new MySQLAuctionRepository());
        map.put("OrderItemRepository", new MySQLOrderItemRepository());

        ((MySQLRequestRepository) map.get("RequestRepository")).initializeRepositories(this);
    }

    public void initializeFakeRep() {
        map.put("CategoryRepository", new FakeCategoryRepository());
        map.put("ProductRepository", new FakeProductRepository());
        map.put("UserRepository", new FakeUserRepository());
        map.put("CommentRepository", new FakeCommentRepository());
        map.put("RatingRepository", new FakeRateRepository());
        map.put("PromoRepository", new FakePromoRepository());
        map.put("ProductSellerRepository", new FakeProductSellerRepository());
        map.put("OffRepository", new FakeOffRepository());
        map.put("OrderRepository", new FakeOrderRepository());
    }

    public Repository<?> getRepository(String repositoryName) {
        return map.get(repositoryName);
    }


}
