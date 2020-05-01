package controller.product;

import controller.interfaces.product.IProductController;
import exception.NoAccessException;
import exception.NoObjectWithIdException;
import exception.NotSellerException;
import exception.ObjectAlreadyExistException;
import model.*;
import model.repository.ProductRepository;
import model.repository.ProductSellerRepository;
import model.repository.RepositoryContainer;

public class ProductController implements IProductController {

    ProductRepository productRepository;
    ProductSellerRepository productSellerRepository;

    public ProductController(RepositoryContainer repositoryContainer) {
        this.productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
        this.productSellerRepository = (ProductSellerRepository) repositoryContainer.getRepository("ProductSellerRepository");
    }

    public void createProduct(Product product, String token) throws ObjectAlreadyExistException, NotSellerException {
        if(product == null)
            throw new NullPointerException();
        if(Session.getSession(token).getLoggedInUser().getRole() != Role.SELLER)
            throw new NotSellerException("You must be seller to add product");
        Product productWithSameName = productRepository.getByName(product.getName());
        if(productWithSameName != null)
            throw new ObjectAlreadyExistException("Product with this name already exists", productWithSameName);
        productRepository.addRequest(product);
    }

    public void addSeller(int id, ProductSeller productSeller, String token) throws NotSellerException, NoAccessException {
        if(productSeller == null)
            throw new NullPointerException();
        User user = Session.getSession(token).getLoggedInUser();
        if(user.getRole() != Role.SELLER)
            throw new NotSellerException("You must be seller to add seller");
        if(!productSeller.getSeller().equals(user))
            throw new NoAccessException("You can only add yourself as seller");
        productSellerRepository.addRequest(productSeller);
    }

    public Product getProductById(int id, String token) throws NoObjectWithIdException {
        Product product = productRepository.getById(id);
        if(product == null)
            throw new NoObjectWithIdException("There is no product with this id");
        return product;
    }

    public void editProduct(int id, Product newProduct, String token) throws NoObjectWithIdException, NotSellerException, NoAccessException {
        Product product = productRepository.getById(id);
        if(product != null)
            throw new NoObjectWithIdException("There is no product with this id to change");
        User user = Session.getSession(token).getLoggedInUser();
        if(user.getRole() != Role.SELLER)
            throw new NotSellerException("You must be seller to edit product");
        if(!product.hasSeller(user))
            throw new NoAccessException("You can only change your own products");
        newProduct.setId(id);
        productRepository.editRequest(newProduct);
    }
}