package repository.mysql;

import model.Category;
import repository.CategoryRepository;
import repository.mysql.utils.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySQLCategoryRepository
        extends MySQLRepository<Category> implements CategoryRepository {

    public MySQLCategoryRepository() {
        super(Category.class);
    }

    @Override
    public Category getByName(String name) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Category> cq = cb.createQuery(Category.class);
            Root<Category> root = cq.from(Category.class);

            cq.select(root).where(cb.equal(root.get("name"), name));
            TypedQuery<Category> typedQuery = em.createQuery(cq);

            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
