package repository.mysql;

import model.Order;
import model.Product;
import model.Seller;
import repository.OrderRepository;
import repository.mysql.utils.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class MySQLOrderRepository
    extends MySQLRepository<Order> implements OrderRepository {
    public MySQLOrderRepository() {
        super(Order.class);
    }

    @Override
    public List<Order> getAllCustomerOrders(int customerId) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Order> cq = cb.createQuery(Order.class);
            Root<Order> root = cq.from(Order.class);

            cq.select(root).where(cb.equal(root.get("customer_id"), customerId));
            TypedQuery<Order> typedQuery = em.createQuery(cq);

            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Order> getAllSellerOrders(int sellerId) {
        // TODO: use criteria query to get orders which has this seller in it's items
        return null;
    }
}
