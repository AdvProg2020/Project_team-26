package model.repository;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class RepositoryContainer {

    private Map<String, Repository> map;

    public RepositoryContainer() {
        map = new HashMap<>();
    }

    public void initialize() {
    }

    public Repository<?> getRepository(String repositoryName) {
        return map.get(repositoryName);
    }
}
