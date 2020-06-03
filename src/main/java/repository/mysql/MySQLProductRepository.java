package repository.mysql;

import model.*;
import repository.ProductRepository;
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
import java.util.Map;

public class MySQLProductRepository
        extends MySQLRepository<Product> implements ProductRepository {

    private RequestRepository requestRepository;

    public MySQLProductRepository() {
        super(Product.class);
        requestRepository = (RequestRepository) MySQLRepository.repositoryContainer.getRepository("RequestRepository");
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
    public void addRequest(Product product, User requestedBy) {
        product.setStatus(Status.DEACTIVE);
        Request request = new Request(requestedBy, new Date(), RequestType.ADD, RequestStatus.PENDING);
        request.setProduct(product);
        if(product.getSellerList().size() > 0) {
            request.setProductSeller(product.getSellerList().get(0));
            product.getSellerList().forEach(productSeller -> productSeller.setStatus(Status.DEACTIVE));
        }
        requestRepository.save(request);
    }

    @Override
    public void editRequest(Product product, User requestedBy) {
        Product oldProduct = getById(product.getId());

        if(oldProduct.getName() != product.getName()) {
            Request request = new Request(requestedBy, new Date(), RequestType.EDIT, RequestStatus.PENDING);
            request.setProduct(oldProduct);
            request.setForEdit("name", product.getName());
            requestRepository.save(request);
        }

        if(oldProduct.getBrand() != product.getBrand()) {
            Request request = new Request(requestedBy, new Date(), RequestType.EDIT, RequestStatus.PENDING);
            request.setProduct(oldProduct);
            request.setForEdit("brand", product.getBrand());
            requestRepository.save(request);
        }

        if(oldProduct.getDescription() != product.getDescription()) {
            Request request = new Request(requestedBy, new Date(), RequestType.EDIT, RequestStatus.PENDING);
            request.setProduct(oldProduct);
            request.setForEdit("description", product.getDescription());
            requestRepository.save(request);
        }

        if(!oldProduct.getCategory().equals(product.getCategory())) {
            Request request = new Request(requestedBy, new Date(), RequestType.EDIT, RequestStatus.PENDING);
            request.setProduct(oldProduct);
            request.setForEdit("category", "" + product.getCategory());
            requestRepository.save(request);
        }

        // TODO: Process changes in category feature
    }

    @Override
    public void deleteRequest(int id, User requestedBy) {
        Request request = new Request(requestedBy, new Date(), RequestType.DELETE, RequestStatus.PENDING);
        request.setProduct(getById(id));
        requestRepository.save(request);
    }

    //*****************************
    // Not useful anymore
    @Override
    public void acceptRequest(int requestId) {
        ProductRequest request = getProductRequestById(requestId);
        switch (request.getRequestType()) {
            case ADD:
                request.getSellerList().get(0).setRequestStatus(RequestStatus.ACCEPTED);
            case EDIT:
                save(request.getProduct());
                request.setRequestStatus(RequestStatus.ACCEPTED);
                persistRequest(request);
                break;
            case DELETE:
                delete(request.getMainProduct());
                break;
        }
    }

    @Override
    public void rejectRequest(int requestId) {
        ProductRequest request = getProductRequestById(requestId);
        if(request.getRequestType() == RequestType.ADD) {
            request.getSellerList().get(0).setRequestStatus(RequestStatus.REJECTED);
        }
        request.setRequestStatus(RequestStatus.REJECTED);
        persistRequest(request);
    }

    @Override
    public ProductRequest getProductRequestById(int requestId) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            return em.find(ProductRequest.class, requestId);
        } catch (Exception e) {
            return null;
        }
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
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProductRequest> cq = cb.createQuery(ProductRequest.class);
            Root<ProductRequest> root = cq.from(ProductRequest.class);

            if(isAscending) {
                cq.orderBy(cb.asc(root.get(sortField)));
            } else {
                cq.orderBy(cb.desc(root.get(sortField)));
            }
            cq.select(root);
            cq.where(cb.equal(root.get("request_status"), "PENDING"));
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
            cq.where(cb.equal(root.get("requested_by_id"), sellerId));
            TypedQuery<ProductRequest> typedQuery = em.createQuery(cq);

            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }
    //*****************************

    @Override
    public List<Product> getAllProductsWithFilterForSellerId(Map<String, String> filter, String fieldName, boolean isAscending, int id) {
        return null;
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
        List<Product> allProducts = getAllSortedAndFiltered(filter, sortField, isAscending);// TODO: define properly
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
