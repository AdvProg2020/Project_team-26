package controller.category;

import controller.interfaces.category.ICategoryController;
import exception.InvalidTokenException;
import exception.NoAccessException;
import exception.*;
import exception.ObjectAlreadyExistException;
import model.*;
import model.repository.CategoryRepository;
import model.repository.ProductRepository;
import model.repository.RepositoryContainer;

import java.util.List;

public class CategoryController implements ICategoryController {
    CategoryRepository categoryRepository;
    ProductRepository productRepository;

    public CategoryController(RepositoryContainer repositoryContainer) {
        this.categoryRepository = (CategoryRepository) repositoryContainer.getRepository("CategoryRepository");
        this.productRepository = (ProductRepository) repositoryContainer.getRepository("ProductRepository");
    }

    @Override
    public int addCategory(int patternId, String newCategoryName, String token) throws InvalidIdException, ObjectAlreadyExistException, NoAccessException, InvalidTokenException {
        checkAccessOfUser(token, "only manager can add category");
        if (checkCategoryExistByName(newCategoryName))
            throw new ObjectAlreadyExistException("the category name should be uniq and this name is already taken", null);
        Category parentCategory = checkParentCategory(patternId);
        Category category = new Category(newCategoryName);
        if (parentCategory == null) {
            category.setParent(null);
        } else {
            category.setParent(parentCategory);
            parentCategory.addSubCategory(category);
            categoryRepository.save(parentCategory);
        }
        categoryRepository.save(category);
        return category.getId();
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
        if (session.getLoggedInUser().getRole() != Role.ADMIN)
            throw new NoAccessException(message);

    }

    @Override
    public void addAttribute(int id, String attributeName, String attribute, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
        checkAccessOfUser(token, "only manager can change or add attribute");
        Category category = getCategoryByIdWithCheck(id);
        CategoryFeature categoryFeature = new CategoryFeature(attributeName, FeatureType.DOUBLE);
        //TODO
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
    public void removeACategory(int id, int parentId, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
        checkAccessOfUser(token, "only manager can remove the Category");
        Category parentCategory = checkParentCategory(parentId);
        Category category = getCategoryByIdWithCheck(id);
        categoryParentCheckingException(id, parentId);
        parentCategory.getSubCategory().remove(category);
        categoryRepository.save(parentCategory);
        categoryRepository.delete(id);
    }

    private Category getCategoryByIdWithCheck(int id) throws InvalidIdException {
        Category category = (Category) categoryRepository.getById(id);
        if (category == null)
            throw new InvalidIdException("no category exist");
        return category;
    }

    private void categoryParentCheckingException(int id, int parentId) throws InvalidIdException {
        Category category = (Category) categoryRepository.getById(id);
        for (Category sub : categoryRepository.getById(parentId).getSubCategory()) {
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
    public List<Category> getAllCategories(int id, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
        checkAccessOfUser(token,"you are not manager.");
        if (id == 0) {
            return categoryRepository.getAll();
        }
        Category category = getCategoryByIdWithCheck(id);
        return category.getSubCategory();
    }

    public List<CategoryFeature> getAttribute(int id, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
        checkAccessOfUser(token,"you are not manager.");
        Category category = getCategoryByIdWithCheck(id);
        return category.getFeatures();
    }

    @Override
    public Category getCategory(int id, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
        checkAccessOfUser(token,"you are not manager.");
        Category category = getCategoryByIdWithCheck(id);
        return category;
    }

    @Override
    public List<Product> getProducts(int id, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
        checkAccessOfUser(token,"you are not manager.");
        Category category = getCategoryByIdWithCheck(id);
        return category.getProducts();
    }
}
