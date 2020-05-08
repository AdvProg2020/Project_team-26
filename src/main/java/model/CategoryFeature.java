package model;

public class CategoryFeature {

    private String featureName;
    private FeatureType featureType;

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
