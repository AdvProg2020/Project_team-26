package controller.category;

import java.util.List;

public class Category {
    private String name;
    private List<String> products;
    private String attribute;
    private List<Category> subcategories;

    public String getName() {
        return name;
    }

    public List<String> getProducts() {
        return products;
    }

    public String getAttribute() {
        return attribute;
    }

    public List<Category> getSubcategories() {
        return subcategories;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setSubcategories(List<Category> subcategories) {
        this.subcategories = subcategories;
    }
}
