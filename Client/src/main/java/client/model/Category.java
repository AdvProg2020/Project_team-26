package client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Category {
    private int id;
    private String name;
    private Category parent;
    private List<CategoryFeature> features;

    @JsonCreator
    public Category(@JsonProperty("id") int id,
                    @JsonProperty("name") String name,
                    @JsonProperty("parent") Category parent,
                    @JsonProperty("features") List<CategoryFeature> features) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.features = features;
    }
    public Category() {
        features = new ArrayList<>();

    }

    public void setName(String name) {
        this.name = name;
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
}


