package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "category_id")
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> subCategory;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

    @ManyToMany
    @JoinTable(
            name = "category_category_feature",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "category_feature_id"))
    private List<CategoryFeature> features;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
//        features = new ArrayList<CategoryFeature>();
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

//    public List<CategoryFeature> getFeatures() {
//        return features;
//    }

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
    public boolean equals(Object obj) {
        if ((obj instanceof Category))
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
//        this.features.forEach(i -> category.features.add(i));
        return category;
    }
}


