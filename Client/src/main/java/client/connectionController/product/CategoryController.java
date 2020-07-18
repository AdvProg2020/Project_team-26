package client.connectionController.product;

import client.connectionController.interfaces.category.ICategoryController;
import client.exception.InvalidIdException;
import client.exception.*;
import client.model.Category;
import client.model.Product;
import client.model.enums.FeatureType;


import java.util.List;
import java.util.Map;

public class CategoryController implements ICategoryController {


    public void addCategory(int patternId, Category newCategory, String token) throws InvalidIdException, ObjectAlreadyExistException, NoAccessException, InvalidTokenException {
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

    public void addAttribute(int id, String attributeName, FeatureType featureType, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
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

    public void removeAttribute(int id, String attributeName, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
        checkAccessOfUser(token, "only manager can remove attribute");
        Category category = getCategoryByIdWithCheck(id);
        CategoryFeature categoryFeature = getCategoryFeature(category, attributeName);
        category.getFeatures().remove(categoryFeature);
        categoryRepository.save(category);
    }


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

    public List<Category> getAllCategories(int id, String token) throws InvalidIdException {
        if (id == 0) {
            return categoryRepository.getAll();
        }
        Category category = getCategoryByIdWithCheck(id);
        return category.getSubCategory();
    }

    public void getExceptionOfIfCategoryExist(int id, String token) throws InvalidIdException {

    }

    public List<CategoryFeature> getAttribute(int id, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
        checkAccessOfUser(token, "you are not manager.");
        Category category = getCategoryByIdWithCheck(id);
        return category.getFeatures();
    }

    public Category getCategory(int id, String token) throws InvalidIdException {
        Category category = getCategoryByIdWithCheck(id);
        return category;
    }

    public List<Category> getSubCategories(int id, String token) throws InvalidIdException {
        Category category = getCategoryByIdWithCheck(id);
        return category.getSubCategory();
    }

    public Category getCategoryByName(String name, String token) throws InvalidIdException, InvalidIdException {
        if (!checkCategoryExistByName(name)) {
            throw new InvalidIdException("The specified category does not exist.");
        } else {
            return categoryRepository.getByName(name);
        }
    }

    public List<Product> getProducts(int id, String token) throws InvalidIdException, NoAccessException, InvalidTokenException {
        checkAccessOfUser(token, "you are not manager.");
        Category category = getCategoryByIdWithCheck(id);
        return category.getProducts();
    }

    public List<Product> getAllProducts(Map<String, String> filter, String sortField, boolean isAscending, int startIndex, int endIndex, int id, String token) throws InvalidIdException {
        Pageable page = createPage(sortField, isAscending, startIndex, endIndex);
        Category category = categoryRepository.getById(id);
        if (category == null) {
            throw new InvalidIdException("No such category exists");
        } else {
            filter.put("category", "" + id);
            return productRepository.getAllSortedAndFiltered(filter, page);
        }
    }

    public List<Product> getAllProductsInOff(Map<String, String> filter, String sortField, boolean isAscending, int startIndex, int endIndex, int id, String token) throws InvalidIdException {
        Pageable page = createPage(sortField, isAscending, startIndex, endIndex);
        Category category = categoryRepository.getById(id);
        if (category == null) {
            throw new InvalidIdException("No such category exists");
        } else {
            filter.put("category", "" + id);
            return productRepository.getAllSortedAndFilteredInOff(filter, page);
        }
    }

    public Category getByName(String name) throws InvalidIdException {
        Category category = categoryRepository.getByName(name);
        if (category == null)
            throw new InvalidIdException("no such name exist.");
        return category;
    }
}
