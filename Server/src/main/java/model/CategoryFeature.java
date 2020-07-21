package model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import model.enums.FeatureType;

import javax.persistence.*;
@JsonSerialize(using = CategoryFeatureSerializer.class)
@Entity
@Table(name = "category_feature")
public class CategoryFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_feature_id")
    private int id;

    @Column(name = "name")
    private String featureName;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private FeatureType featureType;

    public CategoryFeature() {
    }

    public CategoryFeature(String featureName, FeatureType type) {
        this.featureName = featureName;
        this.featureType = type;
    }

    public String getFeatureName() {
        return featureName;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public void setFeatureType(FeatureType featureType) {
        this.featureType = featureType;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public int getId() {
        return id;
    }
}
