package model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@JsonSerialize(using = Category.class)
@Entity
@Table(name = "category")
public class Category extends StdSerializer<Category> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @JsonIgnore
    @OneToMany(mappedBy = "parent")
    private List<Category> subCategory;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "category_category_feature",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "category_feature_id"))
    private List<CategoryFeature> features;

    public Category() {
        super(Category.class);
        features = new ArrayList<CategoryFeature>();
        subCategory = new ArrayList<Category>();
        products = new ArrayList<Product>();
    }

    public Category(String name) {
        super(Category.class);
        this.name = name;
        features = new ArrayList<CategoryFeature>();
        subCategory = new ArrayList<Category>();
        products = new ArrayList<Product>();
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
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

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public void addSubCategory(Category category) {

    }

    public void removeSubCategory(Category category) {

    }

    private void attachParents(StringBuilder branch, Category category) {
        branch.append(category.getName());
        if (category.parent == null) {
            return;
        }
        attachParents(branch, category.getParent());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Category))
            return false;
        Category category = (Category) obj;
        if (category.getId() == this.getId())
            return true;
        return false;
    }

    @Override
    public Category clone() {
        Category category = new Category(this.name);
        category.setId(this.id);
        this.subCategory.forEach(i -> category.subCategory.add(i));
        this.products.forEach(i -> category.products.add(i));
        this.features.forEach(i -> category.features.add(i));
        return category;
    }

    @Override
    public void serialize(Category category, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", id);
        jsonGenerator.writeStringField("name", name);
        if(parent.equals(this)) {
            jsonGenerator.writeObjectField("parent", parent);
        } else {
            jsonGenerator.writeObjectField("parent", null);
        }
        jsonGenerator.writeObjectField("features", features);
    }
}


