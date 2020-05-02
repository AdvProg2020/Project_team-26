package controller.category;

import controller.interfaces.category.ICategoryController;
import controller.interfaces.product.IProductController;
import exception.NoAccessException;
import exception.NoObjectWithIdException;
import exception.ObjectAlreadyExistException;
import model.*;
import model.repository.CategoryRepository;
import model.repository.RepositoryContainer;

import java.util.ArrayList;
import java.util.List;

public class CategoryController implements ICategoryController {
    CategoryRepository categoryRepository;
    IProductController productController;

    public CategoryController(RepositoryContainer repositoryContainer) {
        this.categoryRepository = (CategoryRepository) repositoryContainer.getRepository("CategoryRepository");
    }

    @Override
    public int addCategory(int patternId, String newCategoryName, String token) throws NoObjectWithIdException, ObjectAlreadyExistException, NoAccessException {
        checkAccessOfUser(Session.getSession(token), "only manager can add category");
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

    private Category checkParentCategory(int parentId) throws NoObjectWithIdException {
        if (parentId == 0)
            return null;
        Category category = (Category) categoryRepository.getById(parentId);
        if (category == null)
            throw new NoObjectWithIdException("the father Category doesnt exist");
        return category;

    }

    private boolean checkCategoryExistByName(String name) {
        if (categoryRepository.getByName(name) == null)
            return false;
        return true;
    }

    private void checkAccessOfUser(Session session, String message) throws NoAccessException {
        if (session.getLoggedInUser().getRole() == Role.ADMIN)
            throw new NoAccessException(message);

    }

    @Override
    public void addAttribute(int id, String attributeName, String attribute, String token) throws NoObjectWithIdException, NoAccessException {
        checkAccessOfUser(Session.getSession(token), "only manager can change or add attribute");
        Category category = getCategoryByIdWithCheck(id);
        CategoryFeature categoryFeature = new CategoryFeature(attributeName, attribute);
        category.getFeatures().add(categoryFeature);
        categoryRepository.save(category);
    }

    @Override
    public void changeAttribute(int id, String attributeName, String attribute, String token) throws NoObjectWithIdException, NoAccessException {
        checkAccessOfUser(Session.getSession(token), "only manager can change or add attribute");
        Category category = getCategoryByIdWithCheck(id);
        CategoryFeature categoryFeature = getCategoryFeature(category, attributeName);
        categoryFeature.setFeatureValue(attribute);
        categoryRepository.save(categoryFeature);
        categoryRepository.save(category);
    }

    private CategoryFeature getCategoryFeature(Category category, String name) throws NoObjectWithIdException {
        List<CategoryFeature> categoryFeatures = category.getFeatures();
        for (CategoryFeature categoryFeature : categoryFeatures) {
            if (categoryFeature.getFeatureName().equals(name))
                return categoryFeature;
        }
        throw new NoObjectWithIdException("there is no attribute with name" + name);
    }

    @Override
    public void removeAttribute(int id, String attributeName, String attribute, String token) throws NoObjectWithIdException, NoAccessException {
        checkAccessOfUser(Session.getSession(token), "only manager can remove attribute");
        Category category = getCategoryByIdWithCheck(id);
        CategoryFeature categoryFeature = getCategoryFeature(category, attributeName);
        category.getFeatures().remove(categoryFeature);
        categoryRepository.delete(categoryFeature);
        categoryRepository.save(category);
    }


    @Override
    public void removeACategory(int id, int parentId, String token) throws NoObjectWithIdException, NoAccessException {
        checkAccessOfUser(Session.getSession(token), "only manager can remove the Category");
        Category parentCategory = checkParentCategory(parentId);
        Category category = getCategoryByIdWithCheck(id);
        categoryParentCheckingException(id, parentId);
        parentCategory.getSubCategory().remove(category);
        categoryRepository.save(parentCategory);
        categoryRepository.delete(id);
    }

    private Category getCategoryByIdWithCheck(int id) throws NoObjectWithIdException {
        Category category = (Category) categoryRepository.getById(id);
        if (category == null)
            throw new NoObjectWithIdException("no category exist");
        return category;
    }

    private void categoryParentCheckingException(int id, int parentId) throws NoObjectWithIdException {
        Category category = (Category) categoryRepository.getById(id);
        if (!((Category) categoryRepository.getById(parentId)).getSubCategory().contains(category))
            throw new NoObjectWithIdException("the parent are not valid they are not this category parent");
    }

    @Override
    public void addProduct(int id, int productId, String token) throws NoObjectWithIdException, NoAccessException {
        checkAccessOfUser(Session.getSession(token), "only manager can add product to category");
        Category category = getCategoryByIdWithCheck(id);
        Product product = productController.getProductById(productId, token);
        category.getProducts().add(product);
        categoryRepository.save(category);
    }

    @Override
    public void removeProduct(int id, int productId, String token) throws NoObjectWithIdException, NoAccessException {
        checkAccessOfUser(Session.getSession(token), "only manager can remove product");
        Category category = getCategoryByIdWithCheck(id);
        Product product = productController.getProductById(productId, token);
        List<Product> products = category.getProducts();
        if (!products.contains(product))
            throw new NoObjectWithIdException("there is no product in this category by " + productId + " id");
        category.getProducts().remove(product);
        categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories(int id, String token) throws NoObjectWithIdException {
        if (id == 0) {
            return categoryRepository.getAll();
        }
        Category category = getCategoryByIdWithCheck(id);
        return category.getSubCategory();
    }

    public List<CategoryFeature> getAttribute(int id, String token) throws NoObjectWithIdException {
        Category category = getCategoryByIdWithCheck(id);
        return category.getFeatures();
    }

    @Override
    public Category getCategory(int id, String token) throws NoObjectWithIdException {
        Category category = getCategoryByIdWithCheck(id);
        return category;
    }

    @Override
    public List<Product> getProducts(int id, String token) throws NoObjectWithIdException {
        Category category = getCategoryByIdWithCheck(id);
        return category.getProducts();
    }
}
