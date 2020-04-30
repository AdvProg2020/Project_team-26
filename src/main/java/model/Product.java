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

    public int getId() {
        return id;
    }

    public State getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public List<ProductSeller> getSellerList() {
        return sellerList;
    }

    public Category getCategory() {
        return category;
    }

    public List<CategoryFeature> getCategoryFeatures() {
        return categoryFeatures;
    }

    public String getDescription() {
        return description;
    }

    public double getAverageRate() {
        return averageRate;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addSeller(ProductSeller productSeller) {
        this.sellerList.add(productSeller);
    }

    public boolean hasSeller(User seller) {
        return sellerList.stream().anyMatch(s -> s.getSeller().equals(seller));
    }
}
