package model;

import java.util.ArrayList;
import java.util.List;

public class Category implements Savable {

    private int id;
    private boolean isLoaded;
    private String Name;
    private List<CategoryFeature> features;
    private Category parent;
    private List<Category> subCategory;
    private List<Product> products;

    public Category(int id) {
        this.id = id;
        features = new ArrayList<>();
        subCategory = new ArrayList<>();
        products = new ArrayList<>();
    }

    @Override
    public void load() {
        if (!isLoaded) {

            isLoaded = true;
        }
    }

    @Override
    public void save() {

    }
}
