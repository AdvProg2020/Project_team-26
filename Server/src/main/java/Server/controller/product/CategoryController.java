package Server.controller.product;

import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.*;
import exception.ObjectAlreadyExistException;
import model.*;
import model.enums.FeatureType;
import model.enums.Role;
import org.springframework.web.bind.annotation.*;
import repository.CategoryRepository;
import repository.Pageable;
import repository.ProductRepository;
import repository.RepositoryContainer;

import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {
    CategoryRepository categoryRepository;
    ProductRepository productRepository;

    public CategoryController() {

    }

    public CategoryController(RepositoryContainer repositoryContainer) {
        this.categoryRepository = (CategoryRepository) repositoryContainer.getRepository("CategoryRepository");
        this.productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
    }

    @PostMapping("/controller/method/category/add-category")
    public void addCategory(@RequestBody Map info) throws InvalidIdException, ObjectAlreadyExistException, NoAccessException, InvalidTokenException {
        int patternId = (Integer) info.get("patternId");
        Category newCategory = (Category) info.get("newCategory");
        String token = (String) info.get("token");
        checkAccessOfUser(token, "only manager can add category");
        if (checkCategoryExistByName(newCategory.getName()))
            throw new ObjectAlreadyExistException("the category name should be uniq and this name is already taken", newCategory);
        Category parentCategory = checkParentCategory(patternId);
        if (parentCategory == null) {
            newCategory.setParent(categoryRepository.getById(1));
        } else {
            newCategory.setParent(parentCategory);
            parentCategory.addSubCategory(newCategory);
            categoryRepository.save(parentCategory);
        }
        categoryRepository.save(newCategory);
    }

    private Category checkParentCategory(int parentId) throws InvalidIdException {
        if (parentId == 0)
            return null;
        Category category = (Category) categoryRepository.getById(parentId);
        if (category == null)
            throw new InvalidIdException("the father Category doesnt exist");
        return category;

    }

    private boolean checkCategoryExistByName(String name) {
        if (categoryRepository.getByName(name) == null)
            return false;
        return true;
    }

    private void checkAccessOfUser(String token, String message) throws NoAccessException, InvalidTokenException {
        User user = Session.getSession(token).getLoggedInUser();
        if (user == null || user.getRole() != Role.ADMIN)
            throw new NoAccessException(message);
    }

    @PostMapping("/controller/method/category/add-attribute")
    public void addAttribute(@RequestBody Map info) throws InvalidIdException, NoAccessException, InvalidTokenException {
        int id = (Integer) info.get("id");
        String attributeName = (String) info.get("attributeName");
        FeatureType featureType = (FeatureType) info.get("featureType");
        String token = (String) info.get("token");
        checkAccessOfUser(token, "only manager can change or add attribute");
        Category category = getCategoryByIdWithCheck(id);
        CategoryFeature categoryFeature = new CategoryFeature(attributeName, featureType);
        category.getFeatures().add(categoryFeature);
        categoryRepository.save(category);
    }

    private CategoryFeature getCategoryFeature(Category category, String name) throws InvalidIdException {
        List<CategoryFeature> categoryFeatures = category.getFeatures();
        for (CategoryFeature categoryFeature : categoryFeatures) {
            if (categoryFeature.getFeatureName().equals(name))
                return categoryFeature;
        }
        throw new InvalidIdException("there is no attribute with name" + name);
    }

    @PostMapping("/controller/method/category/remove-attribute")
    public void removeAttribute(@RequestBody Map info) throws InvalidIdException, NoAccessException, InvalidTokenException {
        int id = (Integer) info.get("id");
        String attributeName = (String) info.get("attributeName");
        String token = (String) info.get("token");
        checkAccessOfUser(token, "only manager can remove attribute");
        Category category = getCategoryByIdWithCheck(id);
        CategoryFeature categoryFeature = getCategoryFeature(category, attributeName);
        category.getFeatures().remove(categoryFeature);
        categoryRepository.save(category);
    }

    @PostMapping("/controller/method/category/remove-a-category")
    public void removeACategory(@RequestBody Map info) throws InvalidIdException, NoAccessException, InvalidTokenException, NoObjectIdException {
        int id = (Integer) info.get("id");
        int parentId = (Integer) info.get("parentId");
        String token = (String) info.get("token");
        checkAccessOfUser(token, "only manager can remove the Category.");
        Category parentCategory = checkParentCategory(parentId);
        Category category = getCategoryByIdWithCheck(id);
        categoryParentCheckingException(id, parentId);
        if (parentCategory != null) {
            parentCategory.getSubCategory().remove(category);
            categoryRepository.save(parentCategory);
        }
        categoryRepository.delete(id);
    }

    private Category getCategoryByIdWithCheck(int id) throws InvalidIdException {
        Category category = categoryRepository.getById(id);
        if (category == null)
            throw new InvalidIdException("no category exist.");
        return category;
    }

    private void categoryParentCheckingException(int id, int parentId) throws InvalidIdException {
        if (parentId == 0)
            return;
        Category category = getCategoryByIdWithCheck(id);
        for (Category sub : getCategoryByIdWithCheck(parentId).getSubCategory()) {
            if (sub.getId() == category.getId())
                return;
        }
        throw new InvalidIdException("the parent are not valid they are not this category parent");
    }

    @PostMapping("/controller/method/category/add-product")
    public void addProduct(@RequestBody Map info) throws InvalidIdException, NoAccessException, InvalidTokenException {
        int id = (Integer) info.get("id");
        int productId = (Integer) info.get("productId");
        String token = (String) info.get("token");
        checkAccessOfUser(token, "only manager can add product to category");
        Category category = getCategoryByIdWithCheck(id);
        Product product = productRepository.getById(productId);
        if (product == null)
            throw new InvalidIdException("no product exist with " + productId + " id");
        List<Product> products = category.getProducts();
        if (isProductInlist(products, productId))
            throw new InvalidIdException("product by " + productId + " id already exist");
        products.add(product);
        categoryRepository.save(category);
    }

    @PostMapping("/controller/method/category/remove-product")
    public void removeProduct(@RequestBody Map info) throws InvalidIdException, NoAccessException, InvalidTokenException {
        int id = (Integer) info.get("id");
        int productId = (Integer) info.get("productId");
        String token = (String) info.get("token");
        checkAccessOfUser(token, "only manager can remove product");
        Category category = getCategoryByIdWithCheck(id);
        Product product = productRepository.getById(productId);
        if (product == null)
            throw new InvalidIdException("no product exist with " + productId + " id");
        List<Product> products = category.getProducts();
        if (!isProductInlist(products, productId))
            throw new InvalidIdException("there is no product in this category by " + productId + " id");
        products.remove(product);
        categoryRepository.save(category);
    }

    private boolean isProductInlist(List<Product> products, int id) {
        for (Product product : products) {
            if (product.getId() == id)
                return true;
        }
        return false;
    }

    @RequestMapping("/controller/method/category/get-all-categories/{id}")
    public List<Category> getAllCategories(@PathVariable("id") int id) throws InvalidIdException {
        if (id == 0) {
            return categoryRepository.getAll();
        }
        Category category = getCategoryByIdWithCheck(id);
        return category.getSubCategory();
    }

    public void getExceptionOfIfCategoryExist(int id, String token) throws InvalidIdException {

    }

    @PostMapping("/controller/method/category/get-attribute")
    public List<CategoryFeature> getAttribute(@RequestBody Map info) throws InvalidIdException, NoAccessException, InvalidTokenException {
        int id = (Integer) info.get("id");
        String token = (String) info.get("token");
        checkAccessOfUser(token, "you are not manager.");
        Category category = getCategoryByIdWithCheck(id);
        return category.getFeatures();
    }

    @RequestMapping("/controller/method/category/get-category/{id}")
    public Category getCategory(@PathVariable("id") int id) throws InvalidIdException {
        Category category = getCategoryByIdWithCheck(id);
        return category;
    }

    @RequestMapping("/controller/method/category/get-sub-category/{id}")
    public List<Category> getSubCategories(@PathVariable("id") int id) throws InvalidIdException {
        Category category = getCategoryByIdWithCheck(id);
        return category.getSubCategory();
    }

    @RequestMapping("/controller/method/category/get-name-category/{name}")
    public Category getCategoryByName(@PathVariable("name") String name) throws InvalidIdException, InvalidIdException {
        if (!checkCategoryExistByName(name)) {
            throw new InvalidIdException("The specified category does not exist.");
        } else {
            return categoryRepository.getByName(name);
        }
    }

    @PostMapping("/controller/method/category/get-products")
    public List<Product> getProducts(@RequestBody Map info) throws InvalidIdException, NoAccessException, InvalidTokenException {
        int id = (Integer) info.get("id");
        String token = (String) info.get("token");
        checkAccessOfUser(token, "you are not manager.");
        Category category = getCategoryByIdWithCheck(id);
        return category.getProducts();
    }

    @PostMapping("/controller/method/category/get-all-products")
    public List<Product> getAllProducts(@RequestBody Map info) throws InvalidIdException {
        Map<String,String> filter = (Map<String, String>) info.get("filter");
        String sortField = (String) info.get("sortField");
        boolean isAscending = (Boolean) info.get("isAscending");
        int startIndex = (Integer) info.get("startIndex");
        int endIndex = (Integer) info.get("endIndex");
        int id = (Integer) info.get("id");
        String token = (String) info.get("token");
        Pageable page = createPage(sortField, isAscending, startIndex, endIndex);
        Category category = categoryRepository.getById(id);
        if (category == null) {
            throw new InvalidIdException("No such category exists");
        } else {
            filter.put("category", "" + id);
            return productRepository.getAllSortedAndFiltered(filter, page);
        }
    }

    @PostMapping("/controller/method/category/get-all-products-in-off")
    public List<Product> getAllProductsInOff(@RequestBody Map info) throws InvalidIdException {
        Map<String,String> filter = (Map<String, String>) info.get("filter");
        String sortField = (String) info.get("sortField");
        boolean isAscending = (Boolean) info.get("isAscending");
        int startIndex = (Integer) info.get("startIndex");
        int endIndex = (Integer) info.get("endIndex");
        int id = (Integer) info.get("id");
        String token = (String) info.get("token");
        Pageable page = createPage(sortField, isAscending, startIndex, endIndex);
        Category category = categoryRepository.getById(id);
        if (category == null) {
            throw new InvalidIdException("No such category exists");
        } else {
            filter.put("category", "" + id);
            return productRepository.getAllSortedAndFilteredInOff(filter, page);
        }
    }

    @RequestMapping("/controller/method/category/get-by-name/{name}")
    public Category getByName(@PathVariable("name") String name) throws InvalidIdException {
        Category category = categoryRepository.getByName(name);
        if (category == null)
            throw new InvalidIdException("no such name exist.");
        return category;
    }

    private Pageable createPage(String sortField, boolean isAscending, int startIndex, int endIndex) {
        if (isAscending) {
            return new Pageable(startIndex, endIndex - startIndex, sortField, Pageable.Direction.ASCENDING);
        } else {
            return new Pageable(startIndex, endIndex - startIndex, sortField, Pageable.Direction.DESCENDING);
        }
    }
}
