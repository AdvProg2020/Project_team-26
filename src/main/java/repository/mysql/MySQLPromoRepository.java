package repository.mysql;

import model.Product;
import model.Promo;
import repository.PromoRepository;
import repository.Repository;
import repository.mysql.utils.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class MySQLPromoRepository
        extends MySQLRepository<Promo> implements PromoRepository {
    public MySQLPromoRepository() {
        super(Promo.class);
    }

    @Override
    public Promo getByCode(String promoCode) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Promo> cq = cb.createQuery(Promo.class);
            Root<Promo> root = cq.from(Promo.class);

            cq.select(root).where(cb.equal(root.get("promo_code"), promoCode));
            TypedQuery<Promo> typedQuery = em.createQuery(cq);

            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
