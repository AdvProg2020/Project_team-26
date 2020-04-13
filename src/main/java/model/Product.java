package model;

import java.util.ArrayList;
import java.util.List;

public class Product implements Savable {

    private int id;
    private boolean isLoaded;
    private State state;
    private String name;
    private String brand;
    private List<ProductSeller> sellerList;
    private Category category;
    private List<CategoryFeature> categoryFeatures;
    private String description;
    private double averageRate;
    private List<Comment> comments;

    public Product(int id) {
        this.id = id;
        sellerList = new ArrayList<>();
        categoryFeatures = new ArrayList<>();
        comments = new ArrayList<>();
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
