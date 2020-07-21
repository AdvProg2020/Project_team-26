package client.model;

import client.model.enums.FeatureType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class CategoryFeature {
    private int id;
    private String featureName;
    private FeatureType featureType;

    @JsonCreator
    public CategoryFeature(@JsonProperty("id") int id,
                           @JsonProperty("featureName") String featureName,
                           @JsonProperty("featureType") FeatureType featureType) {
        this.id = id;
        this.featureName = featureName;
        this.featureType = featureType;
    }

    public int getId() {
        return id;
    }

    public String getFeatureName() {
        return featureName;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }
}
