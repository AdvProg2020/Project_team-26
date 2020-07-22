package repository.mysql;

import exception.NoObjectIdException;
import model.Auction;
import model.Order;
import model.OrderItem;
import repository.AuctionRepository;
import repository.Pageable;
import repository.mysql.utils.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.Date;
import java.util.List;

public class MySQLAuctionRepository extends MySQLRepository<Auction> implements AuctionRepository {
    public MySQLAuctionRepository() {
        super(Auction.class);
    }

    @Override
    public boolean doesProductSellerExist(int productSellerId) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Auction> cq = cb.createQuery(Auction.class);
            Root<Auction> root = cq.from(Auction.class);

            Predicate productSellerPredicate = cb.equal(root.get("productSeller"), productSellerId);
            Predicate datePredicate = cb.greaterThan(root.get("endDate"), new Date(new java.util.Date().getTime()));
            cq.select(root).where(cb.and(productSellerPredicate, datePredicate));
            TypedQuery<Auction> typedQuery = em.createQuery(cq);

            return typedQuery.getResultList().size() > 0;
        } catch (NoResultException e) {
            return false;
        }
    }
}
