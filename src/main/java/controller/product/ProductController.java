package controller.product;

import interfaces.product.IProductController;
import exception.*;
import model.*;
import repository.ProductRepository;
import repository.ProductSellerRepository;
import repository.RepositoryContainer;

import java.util.List;
import java.util.Map;

public class ProductController implements IProductController {

    ProductRepository productRepository;
    ProductSellerRepository productSellerRepository;

    public ProductController(RepositoryContainer repositoryContainer) {
        this.productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
        this.productSellerRepository = (ProductSellerRepository) repositoryContainer.getRepository("ProductSellerRepository");
    }

    @Override
    public void createProduct(Product product, String token)
            throws ObjectAlreadyExistException, NotSellerException, InvalidTokenException {
        if (product == null)
            throw new NullPointerException();
        if (Session.getSession(token).getLoggedInUser().getRole() != Role.SELLER)
            throw new NotSellerException("You must be seller to add product");
        Product productWithSameName = productRepository.getByName(product.getName());
        if (productWithSameName != null)
            throw new ObjectAlreadyExistException("Product with this name already exists", productWithSameName);
        productRepository.addRequest(product);
    }

    @Override
    public void addSeller(int id, ProductSeller productSeller, String token)
            throws NotSellerException, NoAccessException, InvalidTokenException {
        if (productSeller == null)
            throw new NullPointerException();
        User user = Session.getSession(token).getLoggedInUser();
        if (user.getRole() != Role.SELLER)
            throw new NotSellerException("You must be seller to add seller");
        if (!productSeller.getSeller().equals(user))
            throw new NoAccessException("You can only add yourself as seller");
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
        Session session = Session.getSession(token);
        if (session.getLoggedInUser() == null) {//todo
            throw new NotLoggedINException("You are not Logged in.");
        } else if (session.getLoggedInUser().getRole() == Role.CUSTOMER) {
            throw new NoAccessException("You must be a seller|manager to remove a product");
        } else if (!productRepository.getById(id).hasSeller(session.getLoggedInUser())) {
            throw new NoAccessException("You don't have this item for sale.");
        } else {
            productRepository.deleteRequest(id);
        }
    }

    @Override
    public List<Product> getAllProductWithFilter(Map<String, String> filter, String fieldName, boolean isAscending, String token) {
        return null;
    }

    @Override
    public List<Product> getAllProductWithFilterForSellerId(int ProductSellerId, Map<String, String> filter, String fieldName, boolean isAscending, String token) {
        return null;
    }

    @Override
    public ProductSeller getProductSellerByIdAndSellerId(int productId, String token) throws InvalidIdException, InvalidTokenException, NotLoggedINException, NoAccessException, NoObjectIdException {
        Session session = Session.getSession(token);
        if (session.getLoggedInUser() == null) {
            throw new NotLoggedINException("You are not logged in.");
        } else if (session.getLoggedInUser().getRole() != Role.SELLER) {
            throw new NoAccessException("You must be a seller to do this.");
        } else if (productRepository.getById(productId) == null) {
            throw new NoObjectIdException("The specified Object does not Exist.");
        } else if (!productRepository.getById(productId).hasSeller((Seller) session.getLoggedInUser())) {
            throw new NoAccessException("This Product is not for you.");
        } else {
            return productSellerRepository.getProductSellerByIdAndSellerId(productId, session.getLoggedInUser().getId());
        }
    }

    @Override
    public void editProduct(int id, Product newProduct, String token) throws InvalidIdException, NotSellerException, NoAccessException, InvalidTokenException {
        Product product = productRepository.getById(id);
        if (product != null)
            throw new InvalidIdException("There is no product with this id to change");
        User user = Session.getSession(token).getLoggedInUser();
        if (user.getRole() != Role.SELLER)
            throw new NotSellerException("You must be seller to edit product");
        if (!product.hasSeller(user))
            throw new NoAccessException("You can only change your own products");
        newProduct.setId(id);
        productRepository.editRequest(newProduct);
    }
}
