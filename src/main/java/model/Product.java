package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id", unique = true)
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "description", nullable = false)
    private String description;

    private double averageRate;

    private Category category;
    private List<ProductSeller> sellerList;
    private List<Comment> comments;
    private Map<CategoryFeature, Object> categoryFeatures;

    public Product(int id) {
        this.id = id;
        sellerList = new ArrayList<ProductSeller>();
        categoryFeatures = new HashMap<>();
        comments = new ArrayList<Comment>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getMinimumPrice() {
        long min = Integer.MAX_VALUE;
        for (ProductSeller productSeller : sellerList) {
            if (min > productSeller.getPriceInOff())
                min = productSeller.getPriceInOff();
        }
        return min;
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

    public Map<CategoryFeature, Object> getCategoryFeatures() {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAverageRate(double averageRate) {
        this.averageRate = averageRate;
    }

    public void addSeller(ProductSeller productSeller) {
        this.sellerList.add(productSeller);
    }

    public boolean hasSeller(User seller) {
        return sellerList.stream().anyMatch(s -> s.getSeller().equals(seller));
    }
}
