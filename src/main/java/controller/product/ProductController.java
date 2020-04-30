package controller.product;

import controller.interfaces.product.IProductController;
import exception.NoObjectWithIdException;
import exception.ObjectAlreadyExistException;
import model.Product;
import model.repository.ProductRepository;
import model.repository.RepositoryContainer;

public class ProductController implements IProductController {

    ProductRepository productRepository;

    public ProductController(RepositoryContainer repositoryContainer) {
        this.productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
    }

    public void createProduct(Product product, String token) throws ObjectAlreadyExistException {
        if(product == null)
            throw new NullPointerException();
        Product productWithSameName = productRepository.getByName(product.getName());
        if(productWithSameName != null)
            throw new ObjectAlreadyExistException("Product with this name already exists", productWithSameName);
        productRepository.addRequest(product);
    }

    public Product getProductByName(int id, String token) throws NoObjectWithIdException {
        Product product = productRepository.getById(id);
        if(product == null)
            throw new NoObjectWithIdException("There is no product with this id");
        return product;
    }

    public void editProduct(int id, Product newProduct) throws NoObjectWithIdException {
        if(!productRepository.exist(id))
            throw new NoObjectWithIdException("There is no product with this id to change");
        newProduct.setId(id);
        productRepository.editRequest(newProduct);
    }
}
