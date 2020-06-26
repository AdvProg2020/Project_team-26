package controller.product;

import controller.interfaces.product.IProductController;
import exception.*;
import model.*;
import model.enums.Role;
import repository.*;

import java.util.List;
import java.util.Map;

public class ProductController implements IProductController {

    ProductRepository productRepository;
    ProductSellerRepository productSellerRepository;
    CategoryRepository categoryRepository;
    UserRepository userRepository;

    public ProductController(RepositoryContainer repositoryContainer) {
        this.productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
        this.productSellerRepository = (ProductSellerRepository) repositoryContainer.getRepository("ProductSellerRepository");
        categoryRepository = (CategoryRepository) repositoryContainer.getRepository("CategoryRepository");
        this.userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
    }

    @Override
    public void createProduct(Product product, String token) throws ObjectAlreadyExistException, NotSellerException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if (product == null)
            throw new NullPointerException();
        if (user.getRole() != Role.SELLER)
            throw new NotSellerException("You must be seller to add product");
        Product productWithSameName = productRepository.getByName(product.getName());
        if (productWithSameName != null)
            throw new ObjectAlreadyExistException("Product with this name already exists", productWithSameName);
        if (product.getCategory() == null)
            product.setCategory(categoryRepository.getById(1));
        productRepository.addRequest(product, user);
    }

    @Override
    public void addSeller(int productId, ProductSeller productSeller, String token) throws NotSellerException, NoAccessException, InvalidTokenException {
        if (productSeller == null)
            throw new NullPointerException();
        User user = Session.getSession(token).getLoggedInUser();
        if (user.getRole() != Role.SELLER)
            throw new NotSellerException("You must be seller to add seller");
        productSeller.setSeller((Seller) user);
        productSellerRepository.addRequest(productSeller);
    }

    @Override
    public Product getProductById(int id, String token) throws InvalidIdException {
        Product product = productRepository.getById(id);
        if (product == null)
            throw new InvalidIdException("There is no product with this id");
        return product;
    }

    @Override
    public Product getProductByName(String name, String token) throws NoObjectIdException {
        Product product = productRepository.getByName(name);
        if (product == null)
            throw new NoObjectIdException("There is no product with this name");
        return product;
    }

    @Override
    public void removeProduct(int id, String token) throws InvalidIdException, InvalidTokenException, NoAccessException, NotLoggedINException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {//todo
            throw new NotLoggedINException("You are not Logged in.");
        } else if (user.getRole() == Role.CUSTOMER) {
            throw new NoAccessException("You must be a seller|manager to remove a product");
        } else if (!productRepository.getById(id).hasSeller(user)) {
            throw new NoAccessException("You don't have this item for sale.");
        } else {
            productRepository.deleteRequest(id, user);
        }
    }

    @Override
    public void removeSeller(int productId, int productSellerId, String token) throws InvalidIdException, InvalidTokenException, NoAccessException, NotLoggedINException {
        User user = Session.getSession(token).getLoggedInUser();//TODO
        if (user == null) {//todo
            throw new NotLoggedINException("You are not Logged in.");
        } else if (user.getRole() == Role.CUSTOMER) {
            throw new NoAccessException("You must be a seller|manager to remove a product");
        } else if (productSellerRepository.getById(productSellerId) == null) {
            throw new NoAccessException("You don't have this item for sale.");
        } else if (!productRepository.getById(productId).hasSeller(user)) {
            throw new NoAccessException("You don't have this item for sale.");
        } else {
            productSellerRepository.deleteRequest(productSellerId);
        }
    }


    @Override
    public List<Product> getAllProductWithFilter(Map<String, String> filter, String fieldName, boolean isAscending, int startIndex, int endIndex, String token) {
        Pageable page = createAPage(fieldName, isAscending, startIndex, endIndex);
        return productRepository.getAllSortedAndFiltered(filter, page);
    }

    @Override
    public List<Product> getAllProductWithFilterForSellerId(Map<String, String> filter, String fieldName, boolean isAscending, int startIndex, int endIndex, String token) throws NotLoggedINException, InvalidTokenException, NoAccessException {
        Pageable page = createAPage(fieldName, isAscending, startIndex, endIndex);
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You must be logged in to view all of your products");
        } else if (user.getRole() != Role.SELLER) {
            throw new NoAccessException("Only a seller can view his/her products");
        } else {
            return productRepository.getAllProductsWithFilterForSeller(filter, page, user.getId());
        }
    }

    @Override
    public ProductSeller getProductSellerByIdAndSellerId(int productId, String token) throws InvalidIdException, InvalidTokenException, NotLoggedINException, NoAccessException, NoObjectIdException {
        Session session = Session.getSession(token);
        User user = Session.getSession(token).getLoggedInUser();
        Product product = productRepository.getProductBySellerId(productId, user.getId());
        if (user == null) {
            throw new NotLoggedINException("You are not logged in.");
        } else if (user.getRole() != Role.SELLER) {
            throw new NoAccessException("You must be a seller to do this.");
        } else if (product == null) {
            throw new NoObjectIdException("The specified Object does not Exist.");
        } else {
            return productSellerRepository.getProductSellerByIdAndSellerId(productId, session.getLoggedInUser().getId());
        }
    }

    @Override
    public void editProduct(int id, Product newProduct, String token) throws InvalidIdException, NotSellerException, NoAccessException, InvalidTokenException {
        Product product = productRepository.getById(id);
        if (product == null)
            throw new InvalidIdException("There is no product with this id to change");
        User user = Session.getSession(token).getLoggedInUser();
        if (user.getRole() != Role.SELLER)
            throw new NotSellerException("You must be seller to edit product");
        if (productRepository.getProductBySellerId(id, user.getId()) == null)
            throw new NoAccessException("You can only change your own products");
        newProduct.setId(id);
        productRepository.editRequest(newProduct, user);
    }

    @Override
    public void editProductSeller(int id, ProductSeller newProductSeller, String token) throws InvalidIdException, InvalidTokenException, NotSellerException, NoAccessException {
        ProductSeller productSeller = productSellerRepository.getById(id);
        if (productSeller == null)
            throw new InvalidIdException("There is no productSeller with this id to change");
        User user = Session.getSession(token).getLoggedInUser();
        if (user.getRole() != Role.SELLER)
            throw new NotSellerException("You must be seller to edit productSeller");
        if (productSeller.getSeller().getId() != (user.getId()))
            throw new NoAccessException("You can only change your own products");
        newProductSeller.setId(productSeller.getId());
        productSellerRepository.editRequest(newProductSeller);
    }

    private Pageable createAPage(String sortField, boolean isAscending, int startIndex, int endIndex) {
        if (isAscending) {
            return new Pageable(startIndex, endIndex - startIndex, sortField, Pageable.Direction.ASCENDING);
        } else {
            return new Pageable(startIndex, endIndex - startIndex, sortField, Pageable.Direction.DESCENDING);
        }
    }
}
