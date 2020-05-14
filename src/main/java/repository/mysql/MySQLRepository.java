package repository.mysql;

import repository.Repository;
import repository.mysql.utils.EntityManagerFactoryProvider;
import repository.mysql.utils.EntityManagerProvider;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class MySQLRepository<T> implements Repository<T> {

    protected EntityManagerFactory entityManagerFactory;
    private Class<T> tClass;

    public MySQLRepository(Class<T> tClass) {
        entityManagerFactory = EntityManagerFactoryProvider.getEntityManagerFactory();
        this.tClass = tClass;
    }

    @Override
    public List<T> getAll() {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(tClass);
            Root<T> from = cq.from(tClass);

            CriteriaQuery<T> select = cq.select(from);
            TypedQuery<T> typedQuery = em.createQuery(select);

            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public T getById(int id) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            return em.find(tClass, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void save(T object) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            em.merge(object);
            et.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (et != null) {
                et.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            Object object = em.find(tClass, id);
            em.remove(object);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(T object) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        try {
            em.remove(object);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean exist(int id) {
        return getById(id) != null;
    }

    @Override
    public List<T> getAllBySortAndFilter(Map<String, String> filter, String sortField, boolean isAscending) {
        return null;
    }

    protected int getId(Class<?> object) {
        if (object == null)
            return -1;

        return (int) EntityManagerFactoryProvider.getEntityManagerFactory().
                getPersistenceUnitUtil().getIdentifier(object);
    }
}
