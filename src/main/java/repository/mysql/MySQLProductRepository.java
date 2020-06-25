package repository.mysql;

import model.*;
import model.enums.RequestStatus;
import model.enums.RequestType;
import model.enums.Status;
import repository.CategoryRepository;
import repository.Pageable;
import repository.ProductRepository;
import repository.RequestRepository;
import repository.mysql.utils.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MySQLProductRepository
        extends MySQLRepository<Product> implements ProductRepository {

    private RequestRepository requestRepository;
    private CategoryRepository categoryRepository;

    public MySQLProductRepository() {
        super(Product.class);
        requestRepository = (RequestRepository) MySQLRepository.repositoryContainer.getRepository("RequestRepository");
        categoryRepository = (CategoryRepository) MySQLRepository.repositoryContainer.getRepository("CategoryRepository");
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
//        save(product);
        if (product.getSellerList().size() > 0) {
            request.setProductSeller(product.getSellerList().get(0));
            product.getSellerList().forEach(productSeller -> {
                productSeller.setStatus(Status.DEACTIVE);
                productSeller.setProduct(product);
            });
        }
        request.setProduct(product);
        requestRepository.save(request);
    }

    @Override
    public void editRequest(Product product, User requestedBy) {
        Product oldProduct = getById(product.getId());

        if (oldProduct.getName() != product.getName()) {
            Request request = new Request(requestedBy, new Date(), RequestType.EDIT, RequestStatus.PENDING);
            request.setProduct(oldProduct);
            request.setForEdit("name", product.getName());
            requestRepository.save(request);
        }

        if (oldProduct.getBrand() != product.getBrand()) {
            Request request = new Request(requestedBy, new Date(), RequestType.EDIT, RequestStatus.PENDING);
            request.setProduct(oldProduct);
            request.setForEdit("brand", product.getBrand());
            requestRepository.save(request);
        }

        if (oldProduct.getDescription() != product.getDescription()) {
            Request request = new Request(requestedBy, new Date(), RequestType.EDIT, RequestStatus.PENDING);
            request.setProduct(oldProduct);
            request.setForEdit("description", product.getDescription());
            requestRepository.save(request);
        }

        if (!oldProduct.getCategory().equals(product.getCategory())) {
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

    @Override
    public List<Product> getAllSortedAndFiltered(Map<String, String> filter, Pageable pageable) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<Product> root = cq.from(Product.class);

            cq.select(root);

            cq.where(filter(filter, cb, root));

            TypedQuery<Product> typedQuery = getPagedQuery(em, cb, cq, root, pageable);

            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Product> getAllSortedAndFilteredInOff(Map<String, String> filter, Pageable pageable) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<Product> root = cq.from(Product.class);
            Join<Product, ProductSeller> productSellerJoin = root.join("sellerList");

            cq.select(root);
            cq.where(cb.and(cb.isNotNull(productSellerJoin.get("priceInOff")), filter(filter, cb, root)));
            TypedQuery<Product> typedQuery = getPagedQuery(em, cb, cq, root, pageable);

            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return null;
        }

//        List<Product> allProducts = getAllSortedAndFiltered(filter, pageable);
//        List<Product> result = new ArrayList<>();
//        for (Product product : allProducts) {
//            for (ProductSeller productSeller : product.getSellerList()) {
//                if(productSeller.getPriceInOff() < productSeller.getPrice()) {
//                    result.add(product);
//                    break;
//                }
//            }
//        }
//        return result;
    }

    @Override
    public List<Product> getAllProductsWithFilterForSeller(Map<String, String> filter, Pageable pageable, int sellerId) {
        EntityManager em = EntityManagerProvider.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<Product> root = cq.from(Product.class);
            Join<Product, ProductSeller> productSellerJoin = root.join("sellerList");

            cq.select(root);
            cq.where(cb.and(cb.equal(productSellerJoin.get("seller"), sellerId), filter(filter, cb, root)));
            TypedQuery<Product> typedQuery = getPagedQuery(em, cb, cq, root, pageable);

            return typedQuery.getResultList();
        } catch (NoResultException e) {
            return null;
        }


//        List<Product> allProducts = getAllSortedAndFiltered(filter, pageable);
//        List<Product> result = new ArrayList<>();
//        for (Product product : allProducts) {
//            for (ProductSeller productSeller : product.getSellerList()) {
//                if(productSeller.getSeller().getId() == sellerId) {
//                    result.add(product);
//                    break;
//                }
//            }
//        }
//        return result;
    }

    private Predicate filter(Map<String, String> filter, CriteriaBuilder cb, Root<Product> root) {
        Predicate predicate = cb.equal(root.get("status"), Status.ACTIVE);
        if (filter == null) {
            return predicate;
        }
        for (String key : filter.keySet()) {
            String value = filter.get(key);
            String[] split = value.split("-");
            switch (key) {
                case "category":
                    Category category = categoryRepository.getById(Integer.parseInt(value));
                    predicate = cb.and(predicate, getCategoryPredicate(cb, root, category));
                    break;
                case "product name":
                    predicate = cb.and(predicate, cb.like(root.get("name"), "%" + value + "%"));
                    break;
                case "brand":
                    predicate = cb.and(predicate, cb.like(root.get("brand"), "%" + value + "%"));
                    break;
                case "description":
                    predicate = cb.and(predicate, cb.like(root.get("description"), "%" + value + "%"));
                    break;
                case "price":
                    predicate = cb.and(predicate, cb.between(root.get("price"), Integer.parseInt(split[0]), Integer.parseInt(split[1])));
                    break;
                case "rate":
                    predicate = cb.and(predicate, cb.between(root.get("rate"), Integer.parseInt(split[0]), Integer.parseInt(split[1])));
                    break;
            }
        }
        return predicate;
    }

    private Predicate getCategoryPredicate(CriteriaBuilder cb, Root<Product> root, Category category) {
        Predicate predicate = cb.equal(root.get("category"), category.getId());
        for (Category subCategory : category.getSubCategory()) {
            if (category.equals(subCategory))
                continue;
            predicate = cb.or(predicate, getCategoryPredicate(cb, root, subCategory));
        }
        return predicate;
    }

}
