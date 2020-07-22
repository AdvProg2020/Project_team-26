package client.model;

import client.model.enums.RequestType;
import client.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.*;

public class Product {
    private int id;
    private String name;
    private String brand;
    private String description;
    private Double averageRate;
    private Integer amountBought;
    private Long price;
    private Category category;
    private byte[] image;
    private List<ProductSeller> sellerList;
    /*@JsonDeserialize(keyUsing = CategoryFeatureDeserializer.class)*/
    @JsonSerialize(using = CategoryFeatureSerializer.class)
    @JsonDeserialize(using = CategoryFeatureDeserializer.class)
    private Map<CategoryFeature, String> categoryFeatures;
    private Status status;

    @JsonCreator
    public Product(@JsonProperty("id") int id,
                   @JsonProperty("name") String name,
                   @JsonProperty("brand") String brand,
                   @JsonProperty("description") String description,
                   @JsonProperty("averageRate") Double averageRate,
                   @JsonProperty("amountBought") Integer amountBought,
                   @JsonProperty("price") Long price,
                   @JsonProperty("category") Category category,
                   @JsonProperty("image") byte[] image,
                   @JsonProperty("sellerList") List<ProductSeller> sellerList,
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
        this.image = image;
        this.sellerList = sellerList;
        this.categoryFeatures = categoryFeatures;
        this.status = status;
    }

    public Product(String name, String brand, String description) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.categoryFeatures = new HashMap<>();
        this.sellerList = new ArrayList<>();
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

    @Override
    public Product clone() {
        Product product = new Product(this.name, this.brand, this.description);
        product.setCategory(category);
        product.setId(id);
        product.setSellerList(sellerList);
        return product;
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

    public String getDescription() {
        return description;
    }

    public byte[] getImage() {
        return image;
    }

    public Double getAverageRate() {
        return averageRate;
    }

    public int getAmountBought() {
        return amountBought;
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

    public void setAverageRate(double averageRate) {
        this.averageRate = averageRate;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void addSeller(ProductSeller productSeller) {
        this.sellerList.add(productSeller);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean hasSeller(User seller) {
        return sellerList.stream().anyMatch(s -> s.getSeller().equals(seller));
    }

    public void setSellerList(List<ProductSeller> sellerList) {
        this.sellerList = sellerList;
    }

    public Status getStatus() {
        return status;
    }

    public Map<CategoryFeature, String> getCategoryFeatures() {
        return categoryFeatures;
    }

    @Override
    public String toString() {
        return name;
    }

    public String littleDescription() {
        return "name: " + name + "\nbrand: " + brand + "\ndescription: " + description + "\nrate :" + averageRate;
    }
}
