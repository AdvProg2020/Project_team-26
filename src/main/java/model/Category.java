package model;

import view.products.all.AllProductViewI;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private int id;
    private String Name;
    private List<CategoryFeature> features;
    private Category parent;
    private List<Category> subCategory;
    private List<Product> products;

    public Category(int id) {
        this.id = id;
        features = new ArrayList<CategoryFeature>();
        subCategory = new ArrayList<Category>();
        products = new ArrayList<Product>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public Category getParent() {
        return parent;
    }

    public List<CategoryFeature> getFeatures() {
        return features;
    }

    public List<Category> getSubCategory() {
        return subCategory;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getCategoriesAndSub() {
        StringBuilder branch = new StringBuilder();
        attachParents(branch, this);
        return branch.toString();
    }

    private void attachParents(StringBuilder branch, Category category) {
        branch.append(category.getName());
        if (category.parent == null) {
            return;
        }
        attachParents(branch, category.getParent());
    }

}


