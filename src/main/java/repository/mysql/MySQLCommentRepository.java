package repository.mysql;

import model.Comment;
import model.Order;
import model.Product;
import model.ProductSeller;
import model.enums.CommentState;
import repository.CommentRepository;
import repository.OrderRepository;
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

public class MySQLCommentRepository extends MySQLRepository<Comment> implements CommentRepository {
    public MySQLCommentRepository() {
        super(Comment.class);
    }


    @Override
    public List<Comment> getConfirmedComments(int productId, Pageable pageable) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
            Root<Comment> root = cq.from(Comment.class);

            cq.select(root);
            cq.where(cb.and(cb.equal(root.get("state"), CommentState.CONFIRMED)));
            TypedQuery<Comment> typedQuery = getPagedQuery(em, cb, cq, root, pageable);

            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
