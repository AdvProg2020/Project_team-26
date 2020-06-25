package repository.mysql;

import model.Order;
import model.OrderItem;
import model.Product;
import model.Seller;
import repository.OrderRepository;
import repository.Pageable;
import repository.mysql.utils.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class MySQLOrderRepository
    extends MySQLRepository<Order> implements OrderRepository {
    public MySQLOrderRepository() {
        super(Order.class);
    }

    @Override
    public List<Order> getAllCustomerOrders(int customerId, Pageable pageable) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Order> cq = cb.createQuery(Order.class);
            Root<Order> root = cq.from(Order.class);

            cq.select(root).where(cb.equal(root.get("customer"), customerId));
            TypedQuery<Order> typedQuery = getPagedQuery(em, cb, cq, root, pageable);

            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Order> getAllSellerOrders(int sellerId, Pageable pageable) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Order> cq = cb.createQuery(Order.class);
            Root<Order> root = cq.from(Order.class);
            Join<Order, OrderItem> orderOrderItemJoin = root.join("items");

            cq.select(root).where(cb.equal(orderOrderItemJoin.get("seller"), sellerId));
            TypedQuery<Order> typedQuery = getPagedQuery(em, cb, cq, root, pageable);

            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
