package repository.mysql;

import model.Request;
import repository.Pageable;
import repository.Repository;
import repository.RepositoryContainer;
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

    protected static RepositoryContainer repositoryContainer;

    protected EntityManagerFactory entityManagerFactory;
    protected Class<T> tClass;

    public MySQLRepository(Class<T> tClass) {
        entityManagerFactory = EntityManagerFactoryProvider.getEntityManagerFactory();
        this.tClass = tClass;
    }

    @Override
    public List<T> getAll() {
        return getAll(null);
    }

    @Override
    public List<T> getAll(Pageable pageable) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(tClass);
            Root<T> root = cq.from(tClass);

            CriteriaQuery<T> select = cq.select(root);
            TypedQuery<T> typedQuery = getPagedQuery(em, cb, cq, root, pageable);

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
//            if (getId(object) == 0)
//                em.persist(object);
//            else
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
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            em.remove(object);
            et.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if (et != null) {
                et.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public boolean exist(int id) {
        return getById(id) != null;
    }

    @Override
    public List<T> getAllSorted(String sortField, boolean isAscending) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(tClass);
            Root<T> root = cq.from(tClass);

            //TODO: define most purchase for product and sort by that
            cq.select(root);
            applySort(sortField, isAscending, cb, cq, root);
            TypedQuery<T> typedQuery = em.createQuery(cq);

            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    protected int getId(T object) {
        if (object == null)
            return -1;

        return (int) EntityManagerFactoryProvider.getEntityManagerFactory().
                getPersistenceUnitUtil().getIdentifier(object);
    }

    protected TypedQuery<T> getPagedQuery(EntityManager em, CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> root, Pageable pageable) {
        if(pageable != null && pageable.isPaged()) {
            if (pageable.getDirection() == Pageable.Direction.ASCENDING) {
                applySort(pageable.getSortField(), true, cb, cq, root);
            } else {
                applySort(pageable.getSortField(), false, cb, cq, root);
            }

            TypedQuery<T> typedQuery = em.createQuery(cq);

            if(pageable.getMaxResult() != 0) {
                typedQuery.setFirstResult(pageable.getFrom());
                typedQuery.setMaxResults(pageable.getMaxResult());
            }

            return typedQuery;
        } else {
            return em.createQuery(cq);
        }
    }

    protected void applySort(String sortField, boolean isAscending, CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> root) {
        if(sortField == null || sortField == "")
            return;
        try {
            if (isAscending) {
                cq.orderBy(cb.asc(root.get(sortField)));
            } else {
                cq.orderBy(cb.desc(root.get(sortField)));
            }
        } catch (Exception e) {
            cq.getOrderList().removeIf(order -> true);
        }
    }

    public static void setRepositoryContainer(RepositoryContainer container) {
        repositoryContainer = container;
    }
}
