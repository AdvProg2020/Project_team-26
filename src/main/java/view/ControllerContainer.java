package view;

import controller.account.AuthenticationController;
import repository.RepositoryContainer;

import java.util.HashMap;
import java.util.Map;

public class ControllerContainer {
    private Map<String, Object> map;
    RepositoryContainer repository;

    public ControllerContainer() {
        map = new HashMap<>();
        repository = new RepositoryContainer();
        initialize();
    }

    private void initialize() {
        map.put("AuthenticationController", new AuthenticationController(repository));
    }

    public Object getController(String controllerName) {
        return map.get(controllerName);
    }
}