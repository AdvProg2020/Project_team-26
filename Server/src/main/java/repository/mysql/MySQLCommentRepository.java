package repository.mysql;

import model.Comment;
import model.enums.CommentState;
import repository.CommentRepository;
import repository.Pageable;
import repository.mysql.utils.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
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
            Predicate confirmedPredicate = cb.equal(root.get("state"), CommentState.CONFIRMED);
            Predicate productPredicate = cb.equal(root.get("product"), productId);
            cq.where(cb.and(confirmedPredicate, productPredicate));
            cq.distinct(true);
            TypedQuery<Comment> typedQuery = getPagedQuery(em, cb, cq, root, pageable);

            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
