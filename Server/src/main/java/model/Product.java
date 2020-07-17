package model;

import model.enums.RequestType;
import model.enums.Status;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "product")
@SecondaryTable(name = "product_additional", pkJoinColumns = @PrimaryKeyJoinColumn(name = "product_id"))
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", unique = true)
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "average_rate", table = "product_additional", insertable = false, updatable = false)
    private Double averageRate;

    @Column(name = "amount", table = "product_additional", insertable = false, updatable = false)
    private Integer amountBought;

    @Column(name = "price", table = "product_additional", insertable = false, updatable = false)
    private Long price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Lob
    @Column(name = "image", columnDefinition = "mediumblob")
    private byte[] image;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private List<ProductSeller> sellerList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @ElementCollection
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @MapKeyColumn(name = "category_feature_id")
    @Column(name = "value")
    @CollectionTable(name = "product_category_feature", joinColumns = @JoinColumn(name = "product_id"))
    private Map<CategoryFeature, String> categoryFeatures;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public Product() {
        sellerList = new ArrayList<>();
        categoryFeatures = new HashMap<>();
    }

    public Product(String name, String brand, String description) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        sellerList = new ArrayList<>();
        categoryFeatures = new HashMap<>();
        comments = new HashSet<>();
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

    public Set<Comment> getComments() {
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

    public ProductRequest createRequest(RequestType requestType) {
        ProductRequest productRequest = new ProductRequest(name, brand, description, category, requestType);
        if (requestType == RequestType.ADD) {
            for (ProductSeller seller : sellerList) {
                productRequest.addSeller(seller.createProductSellerRequest(requestType));
            }
        }
        return productRequest;
    }

    public void setSellerList(List<ProductSeller> sellerList) {
        this.sellerList = sellerList;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public Product clone() {
        Product product = new Product(this.name, this.brand, this.description);
        product.setCategory(category);
        product.setId(id);
        product.setSellerList(sellerList);
        return product;
    }

    public Map<CategoryFeature, String> getCategoryFeatures() {
        return categoryFeatures;
    }

    @Override
    public String toString() {
        return "Id: " + id + "\n" +
                "Name: " + name + "\n" +
                "Brand: " + brand + "\n" +
                "Description: '" + description + "\n" +
                "Category: " + category.getName() + "\n" +
                "Category Features:\n" + categoryFeaturesToString();
    }

    private String categoryFeaturesToString() {
        String result = "";
        for (CategoryFeature key : categoryFeatures.keySet()) {
            String value = categoryFeatures.get(key);
            result += "    " + key.getFeatureName() + ": " + value + "\n";
        }
        return result.trim();
    }
}