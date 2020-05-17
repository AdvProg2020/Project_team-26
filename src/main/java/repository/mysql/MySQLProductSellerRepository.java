package repository.mysql;

import model.ProductSeller;
import model.ProductSellerRequest;
import model.RequestStatus;
import repository.ProductSellerRepository;
import repository.mysql.utils.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class MySQLProductSellerRepository
        extends MySQLRepository<ProductSeller> implements ProductSellerRepository {
    public MySQLProductSellerRepository() {
        super(ProductSeller.class);
    }

    @Override
    public void addRequest(ProductSeller productSeller) {

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
        return null;
    }

    @Override
    public ProductSeller getProductSellerByIdAndSellerId(int productId, int sellerId) {
        return null;
    }
}
