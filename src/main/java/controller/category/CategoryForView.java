package controller.category;

import java.util.List;

public class CategoryForView {
    private String name;
    private List<String> products;
    private String attribute;
    private List<String> subcategories;

    public String getName() {
        return name;
    }

    public List<String> getProducts() {
        return products;
    }

    public String getAttribute() {
        return attribute;
    }

    public List<String> getSubcategories() {
        return subcategories;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setSubcategories(List<CategoryForView> subcategories) {
        this.subcategories = subcategories;
    }
}
