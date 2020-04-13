package model;

public class CategoryFeature {

    private String featureName;
    private String featureValue;

    public CategoryFeature(String featureName) {
        this.featureName = featureName;
    }

    private CategoryFeature(String featureName, String featureValue) {
        this.featureName = featureName;
        this.featureValue = featureValue;
    }

    public CategoryFeature getFeature(String featureValue) {
        return new CategoryFeature(this.featureName, featureValue);
    }

    public String getFeatureName() {
        return featureName;
    }

    public String getFeatureValue() {
        return featureValue;
    }

    public void setFeatureValue(String featureValue) {
        this.featureValue = featureValue;
    }
}
