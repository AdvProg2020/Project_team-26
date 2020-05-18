package repository.mysql;

import exception.NoObjectIdException;
import model.*;
import repository.OffRepository;
import repository.Repository;
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

public class MySQLOffRepository
    extends MySQLRepository<Off> implements OffRepository {

    public MySQLOffRepository() {
        super(Off.class);
    }

    @Override
    public void addRequest(Off off) {
        OffRequest request = off.createRequest(RequestType.ADD);
        persistRequest(request);
    }

    @Override
    public void editRequest(Off off) {
        OffRequest request = off.createRequest(RequestType.EDIT);
        request.setMainOff(off);
        persistRequest(request);
    }

    @Override
    public void deleteRequest(int id) {
        Off off = getById(id);
        OffRequest request = off.createRequest(RequestType.DELETE);
        request.setMainOff(off);
        persistRequest(request);
    }

    @Override
    public void acceptRequest(int requestId) {

    }

    @Override
    public void rejectRequest(int requestId) {
        OffRequest request = getOffRequestById(requestId);
        request.setRequestStatus(RequestStatus.REJECTED);
        persistRequest(request);
    }

    @Override
    public List<Product> getAllProductInOff(Map<String, String> filter, String sortFiled, boolean isAscending) {
        List<Product> allProducts = new MySQLProductRepository().getAllSortedAndFiltered(filter, sortFiled, isAscending);
        List<Product> result = new ArrayList<>();
        for (Product product : allProducts) {
            for (ProductSeller productSeller : product.getSellerList()) {
                if(productSeller.getPriceInOff() < productSeller.getPrice()) {
                    result.add(product);
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public OffRequest getOffRequestById(int requestId) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            return em.find(OffRequest.class, requestId);
        } catch (Exception e) {
            return null;
        }
    }

    private void persistRequest(OffRequest offRequest) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            em.persist(offRequest);
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
    public List<OffRequest> getAllRequests(String sortField, boolean isAscending) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<OffRequest> cq = cb.createQuery(OffRequest.class);
            Root<OffRequest> root = cq.from(OffRequest.class);

            if(isAscending) {
                cq.orderBy(cb.asc(root.get(sortField)));
            } else {
                cq.orderBy(cb.desc(root.get(sortField)));
            }
            cq.select(root);
            TypedQuery<OffRequest> typedQuery = em.createQuery(cq);

            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }
}
