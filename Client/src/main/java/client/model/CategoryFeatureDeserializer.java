package client.model;

import client.model.enums.FeatureType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.boot.jackson.JsonComponent;
import java.io.IOException;

@JsonComponent
public class CategoryFeatureDeserializer extends JsonDeserializer<CategoryFeature> {
    @Override
    public CategoryFeature deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        int id = (int) ((IntNode) node.get("id")).numberValue();
        String featureName = ((ObjectNode)node.get("featureName")).asText();
        FeatureType featureType = makeFeatureType(node.get("featureType").asText());
        return new CategoryFeature(id, featureName, featureType);
    }

    private FeatureType makeFeatureType(String feature) {
        switch (feature) {
            case "INTEGER":
                return FeatureType.INTEGER;
            case "STRING":
                return FeatureType.STRING;
            default:
                return FeatureType.DOUBLE;
        }


    }
}
