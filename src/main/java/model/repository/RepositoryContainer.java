package model.repository;

import model.repository.fake.FakeCategoryFeatureRepository;
import model.repository.fake.FakeCategoryRepository;
import model.repository.fake.FakeProductRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class RepositoryContainer {

    private Map<String, Repository> map;

    public RepositoryContainer() {
        map = new HashMap<>();
    }

    public void initialize() {
        map.put("ProductRepository", new FakeProductRepository());
//        map.put("ProductSellerRepository", new )
        map.put("CategoryRepository", new FakeCategoryRepository());
    }

    public Repository<?> getRepository(String repositoryName) {
        return map.get(repositoryName);
    }
}
