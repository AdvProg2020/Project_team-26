package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import model.enums.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductTemplate {
    private int id;
    private String name;
    private String brand;
    private String description;
    private Double averageRate;
    @JsonIgnore
    private Integer amountBought;
    private Long price;
    private Category category;
    /*@JsonDeserialize(keyUsing = CategoryFeatureDeserializer.class)*/
    private Map<CategoryFeature, String> categoryFeatures;
    private Status status;

    @JsonCreator
    public ProductTemplate(@JsonProperty("id") int id,
                           @JsonProperty("name") String name,
                           @JsonProperty("brand") String brand,
                           @JsonProperty("description") String description,
                           @JsonProperty("averageRate") Double averageRate,
                           @JsonProperty("amountBought") Integer amountBought,
                           @JsonProperty("price") Long price,
                           @JsonProperty("category") Category category,
                           @JsonProperty("categoryFeatures") Map<CategoryFeature, String> categoryFeatures,
                           @JsonProperty("status") Status status) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.averageRate = averageRate;
        this.amountBought = amountBought;
        this.price = price;
        this.category = category;
        this.categoryFeatures = categoryFeatures;
        this.status = status;
    }


    public ProductTemplate(String name, String brand, String description) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.categoryFeatures = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public Product clone() {
        Product product = new Product(this.name, this.brand, this.description);
        product.setCategory(category);
        product.setId(id);
        return product;
    }

    public String getBrand() {
        return brand;
    }


    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }


    public Double getAverageRate() {
        return averageRate;
    }

    public Long getPrice() {
        return price;
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


    public void setStatus(Status status) {
        this.status = status;
    }


    public void setAverageRate(Double averageRate) {
        this.averageRate = averageRate;
    }


    public Status getStatus() {
        return status;
    }

    public Map<CategoryFeature, String> getCategoryFeatures() {
        return categoryFeatures;
    }

    public Integer getAmountBought() {
        return amountBought;
    }

    @Override
    public String toString() {
        return name;
    }

    public String littleDescription() {
        return "name: " + name + "\nbrand: " + brand + "\ndescription: " + description + "\nrate :" + averageRate;
    }
}
