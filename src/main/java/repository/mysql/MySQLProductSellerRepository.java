package repository.mysql;

import model.*;
import repository.ProductSellerRepository;
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

public class MySQLProductSellerRepository
        extends MySQLRepository<ProductSeller> implements ProductSellerRepository {
    public MySQLProductSellerRepository() {
        super(ProductSeller.class);
    }

    @Override
    public void addRequest(ProductSeller productSeller) {
        ProductSellerRequest request = productSeller.createProductSellerRequest(RequestType.ADD);
        request.setProduct(productSeller.getProduct());
        persistRequest(request);
    }

    @Override
    public void editRequest(ProductSeller productSeller) {
        ProductSellerRequest request = productSeller.createProductSellerRequest(RequestType.EDIT);
        request.setMainProductSeller(productSeller);
        persistRequest(request);
    }

    @Override
    public void deleteRequest(ProductSeller productSeller) {
        ProductSellerRequest request = productSeller.createProductSellerRequest(RequestType.DELETE);
        request.setMainProductSeller(productSeller);
        persistRequest(request);
    }

    @Override
    public void acceptRequest(int requestId) {
        ProductSellerRequest request = getProductSellerRequestById(requestId);
        switch (request.getRequestType()) {
            case ADD:
            case EDIT:
                save(request.getMainProductSeller());
                request.setRequestStatus(RequestStatus.ACCEPTED);
                persistRequest(request);
                break;
            case DELETE:
                delete(request.getMainProductSeller());
                break;
        }
    }

    @Override
    public void rejectRequest(int requestId) {
        ProductSellerRequest request = getProductSellerRequestById(requestId);
        request.setRequestStatus(RequestStatus.REJECTED);
        persistRequest(request);
    }

    @Override
    public ProductSellerRequest getProductSellerRequestById(int requestId) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            return em.find(ProductSellerRequest.class, requestId);
        } catch (Exception e) {
            return null;
        }
    }

    private void persistRequest(ProductSellerRequest productSellerRequest) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            em.persist(productSellerRequest);
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
    public List<ProductSellerRequest> getAllRequests(String sortField, boolean isAscending) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProductSellerRequest> cq = cb.createQuery(ProductSellerRequest.class);
            Root<ProductSellerRequest> root = cq.from(ProductSellerRequest.class);

            if(isAscending) {
                cq.orderBy(cb.asc(root.get(sortField)));
            } else {
                cq.orderBy(cb.desc(root.get(sortField)));
            }
            cq.select(root);
            cq.where(cb.equal(root.get("request_status"), "PENDING"));
            TypedQuery<ProductSellerRequest> typedQuery = em.createQuery(cq);

            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

//    EntityManager em = EntityManagerProvider.getEntityManager();
//
//        try {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<ProductSellerRequest> cq = cb.createQuery(ProductSellerRequest.class);
//        Root<ProductSellerRequest> root = cq.from(ProductSellerRequest.class);
//
//        cq.select(root);
//        cq.where(cb.equal(root.get("seller_id"), sellerId));
//        TypedQuery<ProductSellerRequest> typedQuery = em.createQuery(cq);
//
//        return typedQuery.getResultList();
//    } catch (NoResultException e) {
//        return new ArrayList<>();
//    }

    @Override
    public ProductSeller getProductSellerByIdAndSellerId(int productId, int sellerId) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProductSeller> cq = cb.createQuery(ProductSeller.class);
            Root<ProductSeller> root = cq.from(ProductSeller.class);

            cq.select(root);
            cq.where(cb.equal(root.get("product_id"), productId));
            cq.where(cb.equal(root.get("seller_id"), sellerId));
            TypedQuery<ProductSeller> typedQuery = em.createQuery(cq);

            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
