package repository.mysql;

import model.*;
import model.Order;
import repository.OrderRepository;
import repository.Pageable;
import repository.mysql.utils.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MySQLOrderRepository
    extends MySQLRepository<Order> implements OrderRepository {
    public MySQLOrderRepository() {
        super(Order.class);
    }

    @Override
    public void save(Order order) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        EntityTransaction et = null;
        try {
            Set<User> savedUsers = new HashSet<>();
            et = em.getTransaction();
            et.begin();
            if (getId(order) == 0) {
                em.persist(order);
                em.merge(order.getCustomer());
                savedUsers.add(order.getCustomer());

                for (OrderItem item : order.getItems()) {
                    if(!savedUsers.contains(item.getSeller())) {
                        savedUsers.add(item.getSeller());
                        em.merge(item.getSeller());
                    }
                }
            }
            else
                em.merge(order);
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
            Fetch<Order, OrderItem> orderOrderItemFetch = root.fetch("items");
            Join<Order, OrderItem> orderOrderItemJoin = (Join) orderOrderItemFetch;

            cq.select(root).where(cb.equal(orderOrderItemJoin.get("seller"), sellerId));
            TypedQuery<Order> typedQuery = getPagedQuery(em, cb, cq, root, pageable);

            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<OrderItem> getSellerOrderItems(int id, int orderId) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<OrderItem> cq = cb.createQuery(OrderItem.class);
            Root<OrderItem> root = cq.from(OrderItem.class);

            Predicate sellerPredicate = cb.equal(root.get("seller"), id);
            Predicate orderPredicate = cb.equal(root.get("order"), orderId);
            cq.select(root).where(cb.and(orderPredicate, sellerPredicate));
            TypedQuery<OrderItem> typedQuery = em.createQuery(cq);

            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
