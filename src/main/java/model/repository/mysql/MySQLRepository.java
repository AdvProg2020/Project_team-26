package model.repository.mysql;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MySQLRepository<T> {

    protected EntityManagerFactory entityManagerFactory;

    public MySQLRepository() {
        this.entityManagerFactory =
                Persistence.createEntityManagerFactory("group26");
    }



    public void closeEntityManagerFactory() {
        entityManagerFactory.close();
    }
}
