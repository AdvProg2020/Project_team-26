package repository.mysql;

import model.*;
import repository.*;
import repository.mysql.utils.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class MySQLRequestRepository
 extends MySQLRepository<Request> implements RequestRepository {

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private ProductSellerRepository productSellerRepository;
    private OffRepository offRepository;

    public MySQLRequestRepository() {
        super(Request.class);
    }

    public void initializeRepositories(RepositoryContainer repositoryContainer) {
        categoryRepository = (CategoryRepository) repositoryContainer.getRepository("CategoryRepository");
        productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
        productSellerRepository = (ProductSellerRepository) repositoryContainer.getRepository("ProductSellerRepository");
        offRepository = (OffRepository) repositoryContainer.getRepository("OffRepository");
    }

    @Override
    public void accept(int id) {
        Request request = getById(id);
        request.setRequestStatus(RequestStatus.ACCEPTED);
        switch (request.getType()) {
            case "Product":
                acceptProduct(request);
                break;
            case "ProductSeller":
                acceptProductSeller(request);
                break;
            case "Off":
                acceptOff(request);
                break;
        }
    }

    @Override
    public void reject(int id) {
        Request request = getById(id);
        request.setRequestStatus(RequestStatus.REJECTED);
        save(request);
    }

    private void acceptProduct(Request request) {
        Product product = request.getProduct();
        switch (request.getRequestType()) {
            case ADD:
                product.setStatus(Status.ACTIVE);
                productRepository.save(product);
                product.getSellerList().forEach(productSeller -> {
                    productSeller.setStatus(Status.ACTIVE);
                    productSellerRepository.save(productSeller);
                });
                break;
            case EDIT:
                acceptEditProduct(product, request.getFieldName(), request.getNewValue());
                break;
            case DELETE:
                product.setStatus(Status.DEACTIVE);
                productRepository.save(product);
                break;
        }
    }

    private void acceptEditProduct(Product product, String fieldName, String newValue) {
        switch (fieldName) {
            case "name":
                product.setName(newValue);
                break;
            case "brand":
                product.setBrand(newValue);
                break;
            case "description":
                product.setDescription(newValue);
                break;
            case "category":
                try {
                    int categoryId = Integer.parseInt(newValue);
                    product.setCategory(categoryRepository.getById(categoryId));
                } catch (Exception e) {
                    return;
                }
                break;
        }
        productRepository.save(product);
    }

    private void acceptProductSeller(Request request) {
        ProductSeller productSeller = request.getProductSeller();
        switch (request.getRequestType()) {
            case ADD:
                productSeller.setStatus(Status.ACTIVE);
                productSellerRepository.save(productSeller);
                break;
            case EDIT:
                acceptEditProductSeller(productSeller, request.getFieldName(), request.getNewValue());
                break;
            case DELETE:
                productSeller.setStatus(Status.DEACTIVE);
                productSellerRepository.save(productSeller);
                break;
        }
    }

    private void acceptEditProductSeller(ProductSeller productSeller, String fieldName, String newValue) {
        switch (fieldName) {
            case "price":
                try {
                    long price = Long.parseLong(newValue);
                    productSeller.setPrice(price);
                } catch (Exception e) {
                    return;
                }
                break;
            case "remainingItems":
                try {
                    int remainingItems = Integer.parseInt(newValue);
                    productSeller.setRemainingItems(remainingItems);
                } catch (Exception e) {
                    return;
                }
                break;
        }
        productSellerRepository.save(productSeller);
    }

    private void acceptOff(Request request) {
        Off off = request.getOff();
        switch (request.getRequestType()) {
            case ADD:
            case EDIT:
                off.setStatus(Status.ACTIVE);
                offRepository.save(off);
                break;
            case DELETE:
                off.setStatus(Status.DEACTIVE);
                offRepository.save(off);
                break;
        }
    }

    @Override
    public List<Request> getAllPending(Pageable pageable) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Request> cq = cb.createQuery(Request.class);
            Root<Request> root = cq.from(Request.class);

            cq.select(root);
            cq.where(cb.equal(root.get("requestStatus"), RequestStatus.PENDING));
            TypedQuery<Request> typedQuery = getPagedQuery(em, cb, cq, root, pageable);
            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }
}
