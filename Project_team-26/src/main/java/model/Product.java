package model;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private int id;
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
        sellerList = new ArrayList<ProductSeller>();
        categoryFeatures = new ArrayList<CategoryFeature>();
        comments = new ArrayList<Comment>();
    }
}
