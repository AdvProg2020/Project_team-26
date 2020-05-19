package repository.mysql;

import model.*;
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

            cq.select(root).where(cb.equal(root.get("role"), Role.ADMIN));
            TypedQuery<User> typedQuery = em.createQuery(cq);

            return typedQuery.getResultList().size() > 0;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public boolean hasBoughtProduct(int customerId, int productId) {
        try {
            Customer customer = (Customer) getById(customerId);
            for (Order order : customer.getOrders()) {
                for (OrderItem item : order.getItems()) {
                    if(item.getProduct().getId() == productId) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
