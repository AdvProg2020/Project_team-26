package repository.mysql;

import model.*;
import repository.ProductSellerRepository;
import repository.RequestRepository;
import repository.mysql.utils.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySQLProductSellerRepository
        extends MySQLRepository<ProductSeller> implements ProductSellerRepository {

    private final RequestRepository requestRepository;

    public MySQLProductSellerRepository() {
        super(ProductSeller.class);
        requestRepository = (RequestRepository) MySQLRepository.repositoryContainer.getRepository("RequestRepository");
    }

    @Override
    public void addRequest(ProductSeller productSeller) {
        productSeller.setStatus(Status.DEACTIVE);
        Request request = new Request(productSeller.getSeller(), new Date(), RequestType.ADD, RequestStatus.PENDING);
        request.setProductSeller(productSeller);
        requestRepository.save(request);
    }

    @Override
    public void editRequest(ProductSeller productSeller) {
        ProductSeller oldProductSeller = getById(productSeller.getId());

        if(oldProductSeller.getPrice() != productSeller.getPrice()) {
            Request request = new Request(productSeller.getSeller(), new Date(), RequestType.EDIT, RequestStatus.PENDING);
            request.setProductSeller(oldProductSeller);
            request.setForEdit("price", "" + productSeller.getPrice());
            requestRepository.save(request);
        }

        if(oldProductSeller.getRemainingItems() != productSeller.getRemainingItems()) {
            Request request = new Request(productSeller.getSeller(), new Date(), RequestType.EDIT, RequestStatus.PENDING);
            request.setProductSeller(oldProductSeller);
            request.setForEdit("remainingItems", "" + productSeller.getRemainingItems());
            requestRepository.save(request);
        }
    }

    @Override
    public void deleteRequest(int id) {
        ProductSeller productSeller = getById(id);
        Request request = new Request(productSeller.getSeller(), new Date(), RequestType.DELETE, RequestStatus.PENDING);
        request.setProductSeller(productSeller);
        requestRepository.save(request);
    }

    //*****************************
    // Not useful anymore
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

            if (isAscending) {
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
    //*****************************

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
            cq.where(cb.equal(root.get("product"), productId));
            cq.where(cb.equal(root.get("seller"), sellerId));
            TypedQuery<ProductSeller> typedQuery = em.createQuery(cq);

            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
