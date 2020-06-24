package repository.mysql;

import model.*;
import model.enums.RequestStatus;
import model.enums.RequestType;
import model.enums.Status;
import repository.ProductSellerRepository;
import repository.RequestRepository;
import repository.mysql.utils.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

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
        save(productSeller);
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
            Predicate productPredicate = cb.equal(root.get("product"), productId);
            Predicate sellerPredicate = cb.equal(root.get("seller"), sellerId);
            cq.where(cb.and(productPredicate, sellerPredicate));
            TypedQuery<ProductSeller> typedQuery = em.createQuery(cq);

            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
