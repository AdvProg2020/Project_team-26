package repository.mysql;

import model.Category;
import model.User;
import repository.UserRepository;
import repository.mysql.utils.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class MySQLUserRepository
        extends MySQLRepository<User> implements UserRepository {
    public MySQLUserRepository() {
        super(User.class);
    }

    @Override
    public User getUserByUsername(String username) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);

            cq.select(root).where(cb.equal(root.get("username"), username));
            TypedQuery<User> typedQuery = em.createQuery(cq);

            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean doWeHaveAManager() {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);

            cq.select(root).where(cb.equal(root.get("DTYPE"), "Admin"));
            TypedQuery<User> typedQuery = em.createQuery(cq);

            return typedQuery.getSingleResult() != null;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public boolean hasBoughtProduct(int customerId, int productId) {
        return false;
    }
}
