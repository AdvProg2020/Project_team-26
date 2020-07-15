package repository.mysql;

import model.Rate;
import repository.RateRepository;
import repository.mysql.utils.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class MySQLRateRepository
    extends MySQLRepository<Rate> implements RateRepository {
    public MySQLRateRepository() {
        super(Rate.class);
    }

    @Override
    public Rate getCustomerRate(int customerId, int productId) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Rate> cq = cb.createQuery(Rate.class);
            Root<Rate> root = cq.from(Rate.class);

            Predicate productPredicate = cb.equal(root.get("product"), productId);
            Predicate customerPredicate = cb.equal(root.get("customer"), customerId);
            cq.select(root).where(cb.and(productPredicate, customerPredicate));
            TypedQuery<Rate> typedQuery = em.createQuery(cq);

            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
