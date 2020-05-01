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

    public int getCategoryIdByName(String name) throws NoObjectWithIdException, ObjectAlreadyExistException {
        if (!checkCategoryExistByName(name))
            throw new NoObjectWithIdException("the category with " + name + " name doesn't exist");
        return categoryRepository.getByName(name).getId();
    }

    @Override
    public int addCategory(int patternId, String newCategoryName, String token) throws NoObjectWithIdException, ObjectAlreadyExistException, NoAccessException {
        checkAccessOfUser(Session.getSession(token), "only manager can add category");
        CategoryNameWithSameName(newCategoryName);
        Category parentCategory = checkParentCategory(patternId);
        return createNewCategory(newCategoryName, parentCategory);
    }

    private Category checkParentCategory(int parentId) throws NoObjectWithIdException {
        if (parentId == 0)
            return null;
        Category category = (Category) categoryRepository.getById(parentId);
        if (category == null)
            throw new NoObjectWithIdException("the father Category doesnt exist");
        return category;

    }

    private void CategoryNameWithSameName(String name) throws ObjectAlreadyExistException {
        if (checkCategoryExistByName(name))
            throw new ObjectAlreadyExistException("the category name should be uniq and this name is already taken", null);
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

    private int createNewCategory(String name, Category parentCategory) {
        Category category = new Category(name);
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

    @Override
    public void editCategory(int id, ArrayList<String> changes, String token) throws NoObjectWithIdException, NoAccessException {
        checkAccessOfUser(Session.getSession(token), "only manager can edit the fields");
        Category category = getCategoryByIdWithCheck(id);


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
