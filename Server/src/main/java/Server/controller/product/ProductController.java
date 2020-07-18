package Server.controller.product;

import exception.*;
import model.*;
import model.enums.Role;
import org.springframework.web.bind.annotation.*;
import repository.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    ProductRepository productRepository;
    ProductSellerRepository productSellerRepository;
    CategoryRepository categoryRepository;
    UserRepository userRepository;

    public ProductController() {

    }


    public ProductController(RepositoryContainer repositoryContainer) {
        this.productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
        this.productSellerRepository = (ProductSellerRepository) repositoryContainer.getRepository("ProductSellerRepository");
        categoryRepository = (CategoryRepository) repositoryContainer.getRepository("CategoryRepository");
        this.userRepository = (UserRepository) repositoryContainer.getRepository("UserRepository");
    }

    @PostMapping("/controller/method/product/create-product")
    public void createProduct(@RequestBody Map info) throws ObjectAlreadyExistException, NotSellerException, InvalidTokenException {
        Product product = (Product) info.get("product");
        String token = (String) info.get("token");
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

    @PostMapping("/controller/method/product/add-seller")
    public void addSeller(@RequestBody Map info) throws NotSellerException, NoAccessException, InvalidTokenException {
        int productId = (Integer) info.get("productId");
        ProductSeller productSeller = (ProductSeller) info.get("productSeller");
        String token = (String) info.get("token");
        if (productSeller == null)
            throw new NullPointerException();
        User user = Session.getSession(token).getLoggedInUser();
        if (user.getRole() != Role.SELLER)
            throw new NotSellerException("You must be seller to add seller");
        productSeller.setSeller((Seller) user);
        productSellerRepository.addRequest(productSeller);
    }

    @RequestMapping("/controller/method/product/get-product-by-id/{id}")
    public Product getProductById(@PathVariable("id") int id) throws InvalidIdException {
        Product product = productRepository.getById(id);
        if (product == null)
            throw new InvalidIdException("There is no product with this id");
        return product;
    }

    @RequestMapping("/controller/method/product/get-product-by-name/{name}")
    public Product getProductByName(@PathVariable("name") String name) throws NoObjectIdException {
        Product product = productRepository.getByName(name);
        if (product == null)
            throw new NoObjectIdException("There is no product with this name");
        return product;
    }

    @PostMapping("/controller/method/product/remove-product")
    public void removeProduct(@RequestBody Map info) throws InvalidIdException, InvalidTokenException, NoAccessException, NotLoggedINException {
        int id = (Integer) info.get("id");
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {//todo
            throw new NotLoggedINException("You are not Logged in.");
        } else if (user.getRole() == Role.CUSTOMER) {
            throw new NoAccessException("You must be a seller|manager to remove a product");
        } else if (productRepository.getProductBySellerId(id, user.getId()) == null) {
            throw new NoAccessException("You don't have this item for sale.");
        } else {
            productRepository.deleteRequest(id, user);
        }
    }

    @PostMapping("/controller/method/product/remove-seller")
    public void removeSeller(@RequestBody Map info) throws InvalidIdException, InvalidTokenException, NoAccessException, NotLoggedINException {
        int productId = (Integer) info.get("productId");
        int productSellerId = (Integer) info.get("productSellerId");
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();//TODO
        if (user == null) {//todo
            throw new NotLoggedINException("You are not Logged in.");
        } else if (user.getRole() == Role.CUSTOMER) {
            throw new NoAccessException("You must be a seller|manager to remove a product");
        } else if (productSellerRepository.getById(productSellerId) == null) {
            throw new NoAccessException("You don't have this item for sale.");
        } else if (productRepository.getProductBySellerId(productId, user.getId()) == null) {
            throw new NoAccessException("You don't have this item for sale.");
        } else {
            productSellerRepository.deleteRequest(productSellerId);
        }
    }

    @PostMapping("/controller/method/product/get-all-products-with-filter")
    public List<Product> getAllProductWithFilter(@RequestBody Map info) {
        Map<String, String> filter = (Map<String, String>) info.get("filter");
        String fieldName = (String) info.get("fieldName");
        boolean isAscending = (Boolean) info.get("isAscending");
        int startIndex = (Integer) info.get("startIndex");
        int endIndex = (Integer) info.get("endIndex");
        String token = (String) info.get("token");
        Pageable page = createAPage(fieldName, isAscending, startIndex, endIndex);
        return productRepository.getAllSortedAndFiltered(filter, page);
    }

    @PostMapping("/controller/method/product/get-all-products-with-filter-for-seller-id")
    public List<Product> getAllProductWithFilterForSellerId(@RequestBody Map info) throws NotLoggedINException, InvalidTokenException, NoAccessException {
        Map<String, String> filter = (Map<String, String>) info.get("filter");
        String fieldName = (String) info.get("fieldName");
        boolean isAscending = (Boolean) info.get("isAscending");
        int startIndex = (Integer) info.get("startIndex");
        int endIndex = (Integer) info.get("endIndex");
        String token = (String) info.get("token");
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

    @PostMapping("/controller/method/product/get-product-seller-by-id-and-seller-id")
    public ProductSeller getProductSellerByIdAndSellerId(@RequestBody Map info) throws InvalidIdException, InvalidTokenException, NotLoggedINException, NoAccessException, NoObjectIdException {
        int productId = (Integer) info.get("productId");
        String token = (String) info.get("token");
        Session session = Session.getSession(token);
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null) {
            throw new NotLoggedINException("You are not logged in.");
        }
        Product product = productRepository.getProductBySellerId(productId, user.getId());
        if (user.getRole() != Role.SELLER) {
            throw new NoAccessException("You must be a seller to do this.");
        } else if (product == null) {
            throw new NoObjectIdException("The specified Object does not Exist.");
        } else {
            return productSellerRepository.getProductSellerByIdAndSellerId(productId, session.getLoggedInUser().getId());
        }
    }

    @PostMapping("/controller/method/product/edit-product")
    public void editProduct(@RequestBody Map info) throws InvalidIdException, NotSellerException, NoAccessException, InvalidTokenException {
        int id = (Integer) info.get("id");
        Product newProduct = (Product) info.get("newProduct");
        String token = (String) info.get("token");
        User user = Session.getSession(token).getLoggedInUser();
        Product product = productRepository.getProductBySellerId(id, user.getId());
        if (user.getRole() != Role.SELLER)
            throw new NotSellerException("You must be seller to edit product");
        if (product == null)
            throw new NoAccessException("You can only change your own products");
        newProduct.setId(id);
        productRepository.editRequest(newProduct, user);
    }

    @PostMapping("/controller/method/product/edit-product-seller")
    public void editProductSeller(@RequestBody Map info) throws InvalidIdException, InvalidTokenException, NotSellerException, NoAccessException {
        int id = (Integer) info.get("id");
        ProductSeller newProductSeller = (ProductSeller) info.get("newProductSeller");
        String token = (String) info.get("token");
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
