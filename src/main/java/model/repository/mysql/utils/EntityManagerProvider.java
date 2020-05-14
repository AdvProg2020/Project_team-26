package model.repository.mysql.utils;

import javax.persistence.EntityManager;

public class EntityManagerProvider {

    private static EntityManager entityManager = null;

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager =
                    EntityManagerFactoryProvider.getEntityManagerFactory().createEntityManager();
        }
        return entityManager;
    }

    public static void close() {
        if(entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
        entityManager = null;
    }
}
