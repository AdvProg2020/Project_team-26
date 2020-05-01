package controller.category;

import controller.interfaces.category.ICategoryController;
import controller.interfaces.product.IProductController;
import exception.NoAccessException;
import exception.NoObjectWithIdException;
import exception.ObjectAlreadyExistException;
import model.Category;
import model.Product;
import model.Role;
import model.Session;
import model.repository.CategoryRepository;
import model.repository.RepositoryContainer;

import java.util.ArrayList;

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
        checkCategoryNameWithSameName(newCategoryName);
        checkParentCategory(patternId);
        int id = createNewCategory(newCategoryName, patternId).getId();
        return id;
    }

    private void checkParentCategory(int parentId) throws NoObjectWithIdException {
        if (parentId == 0)
            return;
        if (checkCategoryExistById(parentId)) ;
        throw new NoObjectWithIdException("the father Category doesnt exist");
    }

    private void checkCategoryNameWithSameName(String name) throws ObjectAlreadyExistException {
        if (checkCategoryExistByName(name))
            throw new ObjectAlreadyExistException("the category name should be uniq and this name is already taken", null);
    }

    private boolean checkCategoryExistByName(String name) {
        if (categoryRepository.getByName(name) == null)
            return false;
        return true;
    }

    private boolean checkCategoryExistById(int id) {
        if (categoryRepository.getById(id) == null)
            return false;
        return true;
    }

    private void checkAccessOfUser(Session session, String message) throws NoAccessException {
        if (session.getLoggedInUser().getRole() == Role.ADMIN)
            throw new NoAccessException(message);

    }

    private Category createNewCategory(String name, int parentId) {
        Category category = new Category(name);
        if (parentId == 0) {
            category.setParent(null);
        } else {
            category.setParent((Category) categoryRepository.getById(category.getId()));
            Category parentCategory = (Category) categoryRepository.getById(parentId);
            parentCategory.addSubCategory(category);
            categoryRepository.save(parentCategory);
        }
        categoryRepository.save(category);
        return category;
    }

    @Override
    public void editCategory(int id, ArrayList<String> changes, String token) throws NoObjectWithIdException {

    }

    @Override
    public void addAttribute(int id, String attribute, String token) throws NoObjectWithIdException {
        checkCategoryById(id);
        Category category = (Category) categoryRepository.getById(id);
        /*


         */
        categoryRepository.save(category);


    }


    @Override
    public void removeACategory(int id, int parentId, String token) throws NoObjectWithIdException, NoAccessException {
        checkAccessOfUser(Session.getSession(token), "only manager can remove the Category");
        checkParentCategory(parentId);
        isThisParentParentOfCategory(id, parentId);
        checkCategoryById(id);
        Category parentCategory = (Category) categoryRepository.getById(parentId);
        parentCategory.getSubCategory().remove(categoryRepository.getById(id));
        categoryRepository.save(parentCategory);
        categoryRepository.delete(id);
    }

    private void checkCategoryById(int id) throws NoObjectWithIdException {
        if (categoryRepository.getById(id) == null)
            throw new NoObjectWithIdException("no category exist");
    }

    private void isThisParentParentOfCategory(int id, int parentId) throws NoObjectWithIdException {
        Category category = (Category) categoryRepository.getById(id);
        if (!((Category) categoryRepository.getById(parentId)).getSubCategory().contains(category))
            throw new NoObjectWithIdException("the parent are not valid they are not this category parent");
    }

    @Override
    public void addProduct(int id, int productId, String token) throws NoObjectWithIdException, NoAccessException {
        checkAccessOfUser(Session.getSession(token), "only manager can add product to category");
        checkCategoryById(id);
        Product product = productController.getProductById(productId, token);
        Category category = (Category) categoryRepository.getById(id);
        category.getProducts().add(product);
        categoryRepository.save(category);
    }
}
