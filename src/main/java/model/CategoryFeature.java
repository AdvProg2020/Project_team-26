package model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category_feature")
public class CategoryFeature {

    @Id
    @Column(name = "category_feature_id")
    private int id;

    @Column(name = "name")
    private String featureName;

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
}
