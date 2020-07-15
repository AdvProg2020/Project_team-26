package repository.mysql;

import model.Customer;
import model.Order;
import model.OrderItem;
import repository.CustomerRepository;
import repository.Pageable;
import repository.mysql.utils.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class MySQLCustomerRepository
    extends MySQLRepository<Customer> implements CustomerRepository {

    public MySQLCustomerRepository() {
        super(Customer.class);
    }

    @Override
    public List<Customer> getAllProductBuyers(int productId, Pageable pageable) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
            Root<Customer> root = cq.from(Customer.class);
            Join<Customer, Order> customerOrderJoin = root.join("orders");
            Join<Order, OrderItem> orderOrderItemJoin = customerOrderJoin.join("items");

            cq.select(root);
            cq.where(cb.equal(orderOrderItemJoin.get("product"), productId));
            TypedQuery<Customer> typedQuery = getPagedQuery(em, cb, cq, root, pageable);

            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
