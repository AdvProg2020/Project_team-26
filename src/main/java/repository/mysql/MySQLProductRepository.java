package repository.mysql;

import model.Product;
import model.ProductRequest;
import model.ProductSeller;
import model.RequestType;
import repository.ProductRepository;
import repository.mysql.utils.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySQLProductRepository
        extends MySQLRepository<Product> implements ProductRepository {

    public MySQLProductRepository() {
        super(Product.class);
    }

    @Override
    public Product getByName(String name) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<Product> root = cq.from(Product.class);

            cq.select(root).where(cb.equal(root.get("name"), name));
            TypedQuery<Product> typedQuery = em.createQuery(cq);

            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void addRequest(Product product) {
        ProductRequest productRequest = product.createRequest(RequestType.ADD);
        persistRequest(productRequest);
    }

    @Override
    public void acceptRequest(int requestId) {

    }

    @Override
    public void rejectRequest(int requestId) {

    }

    @Override
    public ProductRequest getProductRequestById(int requestId) {
        return null;
    }

    @Override
    public void editRequest(Product product) {
        ProductRequest productRequest = product.createRequest(RequestType.EDIT);
        persistRequest(productRequest);
    }

    @Override
    public void deleteRequest(int id) {
        Product product = getById(id);
        ProductRequest productRequest = product.createRequest(RequestType.DELETE);
        persistRequest(productRequest);
    }

    private void persistRequest(ProductRequest productRequest) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            em.persist(productRequest);
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
    public List<ProductRequest> getAllRequests(String sortField, boolean isAscending) {
        //TODO
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProductRequest> cq = cb.createQuery(ProductRequest.class);
            Root<ProductRequest> root = cq.from(ProductRequest.class);

            cq.select(root);
            TypedQuery<ProductRequest> typedQuery = em.createQuery(cq);

            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<ProductRequest> getAllSellerRequests(int sellerId) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProductRequest> cq = cb.createQuery(ProductRequest.class);
            Root<ProductRequest> root = cq.from(ProductRequest.class);

            cq.select(root);
            cq.where(cb.equal(root.get("created_by_id"), sellerId));
            TypedQuery<ProductRequest> typedQuery = em.createQuery(cq);

            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Product> getAllSortedAndFiltered(Map<String, String> filter, String sortField, boolean isAscending) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<Product> root = cq.from(Product.class);

            applySort(sortField, isAscending, cb, cq, root);
            cq.select(root);

            applyFilter(filter, cb, cq, root);

            TypedQuery<Product> typedQuery = em.createQuery(cq);

            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Product> getAllSortedAndFilteredInOff(Map<String, String> filter, String sortField, boolean isAscending) {
        return null;
    }

    private CriteriaQuery applyFilter(Map<String, String> filter, CriteriaBuilder cb, CriteriaQuery<Product> cq, Root<Product> root) {
        for(String key : filter.keySet()) {
            String value = filter.get(key);
            String[] split = value.split("-");
            switch (key) {
                case "product name":
                    cq.where(cb.like(root.get("name"), "%" + value + "%"));
                    break;
                case "brand":
                    cq.where(cb.like(root.get("brand"), "%" + value + "%"));
                    break;
                case "description":
                    cq.where(cb.like(root.get("description"), "%" + value + "%"));
                    break;
                case "price":
                    cq.select(root).where(cb.between(root.get("price"), Integer.parseInt(split[0]), Integer.parseInt(split[1])));
                    break;
                case "rate":
                    cq.select(root).where(cb.between(root.get("rate"), Integer.parseInt(split[0]), Integer.parseInt(split[1])));
                    break;
            }
        }
        return cq;
    }

}
