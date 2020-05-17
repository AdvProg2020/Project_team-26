package controller.product;

import interfaces.category.ICategoryController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.*;
import exception.ObjectAlreadyExistException;
import model.*;
import repository.CategoryRepository;
import repository.ProductRepository;
import repository.RepositoryContainer;

import java.util.List;
import java.util.Map;

public class CategoryController implements ICategoryController {
    CategoryRepository categoryRepository;
    ProductRepository productRepository;

    public CategoryController(RepositoryContainer repositoryContainer) {
        this.categoryRepository = (CategoryRepository) repositoryContainer.getRepository("CategoryRepository");
        this.productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
    }

    @Override
    public void addCategory(int patternId, Category newCategory, String token) throws InvalidIdException, ObjectAlreadyExistException, NoAccessException, InvalidTokenException {
        checkAccessOfUser(token, "only manager can add category");
        if (checkCategoryExistByName(newCategory.getName()))
            throw new ObjectAlreadyExistException("the category name should be uniq and this name is already taken", newCategory);
        Category parentCategory = checkParentCategory(patternId);
        if (parentCategory == null) {
            newCategory.setParent(null);
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
        Session session = Session.getSession(token);
        if (session.getLoggedInUser() == null || session.getLoggedInUser().getRole() != Role.ADMIN)
            throw new NoAccessException(message);
    }

    @Override
    public void addAttribute(int id, String attributeName, String attribute, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
        checkAccessOfUser(token, "only manager can change or add attribute");
        Category category = getCategoryByIdWithCheck(id);
        CategoryFeature categoryFeature = new CategoryFeature(attributeName, FeatureType.DOUBLE);
        category.getFeatures().add(categoryFeature);
        categoryRepository.save(category);
    }

    @Override
    public void changeAttribute(int id, String attributeName, String attribute, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
        checkAccessOfUser(token, "only manager can change or add attribute");
        Category category = getCategoryByIdWithCheck(id);
        CategoryFeature categoryFeature = getCategoryFeature(category, attributeName);
        categoryFeature.setFeatureName(attribute);
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

    @Override
    public void removeAttribute(int id, String attributeName, String attribute, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
        checkAccessOfUser(token, "only manager can remove attribute");
        Category category = getCategoryByIdWithCheck(id);
        CategoryFeature categoryFeature = getCategoryFeature(category, attributeName);
        category.getFeatures().remove(categoryFeature);
        categoryRepository.save(category);
    }


    @Override
    public void removeACategory(int id, int parentId, String token) throws InvalidIdException, NoAccessException, InvalidTokenException, NoObjectIdException {
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
        Category category = (Category) categoryRepository.getById(id);
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

    @Override
    public void addProduct(int id, int productId, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
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

    @Override
    public void removeProduct(int id, int productId, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
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

    @Override
    public List<Category> getAllCategories(int id, String token) throws InvalidIdException {
        if (id == 0) {
            return categoryRepository.getAll();
        }
        Category category = getCategoryByIdWithCheck(id);
        return category.getSubCategory();
    }

    @Override
    public void getExceptionOfIfCategoryExist(int id, String token) throws InvalidIdException {

    }

    @Override
    public List<Category> getAllCategoriesWithFilter(String sortFiled, boolean isAscending, int id, String token) throws InvalidIdException {
        if (id == 0) {//todo
            return categoryRepository.getAll();
        }
        Category category = getCategoryByIdWithCheck(id);
        return category.getSubCategory();
    }

    public List<CategoryFeature> getAttribute(int id, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
        checkAccessOfUser(token, "you are not manager.");
        Category category = getCategoryByIdWithCheck(id);
        return category.getFeatures();
    }

    @Override
    public Category getCategory(int id, String token) throws InvalidIdException {
        Category category = getCategoryByIdWithCheck(id);
        return category;
    }

    @Override
    public Category getCategoryByName(String name, String token) throws InvalidIdException, InvalidIdException {
        if(!checkCategoryExistByName(name)) {
            throw new InvalidIdException("The specified category does not exist.");
        } else {
            return categoryRepository.getByName(name);
        }
    }

    @Override
    public List<Category> getAllProductWithFilter(Map<String, String> filter, String sortField, boolean isAscending, int id, String token) throws InvalidIdException {
   return null;
    }

    @Override
    public List<Product> getProducts(int id, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
        checkAccessOfUser(token, "you are not manager.");
        Category category = getCategoryByIdWithCheck(id);
        return category.getProducts();
    }

    public Category getByName(String name) throws InvalidIdException {
        Category category = categoryRepository.getByName(name);
        if (category == null)
            throw new InvalidIdException("no such name exist.");
        return category;
    }
}
