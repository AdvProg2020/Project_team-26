package model;

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
}
