package client.model;

import client.model.enums.FeatureType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.boot.jackson.JsonComponent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CategoryFeatureDeserializer extends StdDeserializer<Map<CategoryFeature,String>> {
    /*public CategoryFeatureDeserializer(Class<CategoryFeature> vc) {
        super(vc);
    }
    public CategoryFeatureDeserializer(){
        this(CategoryFeature.class);
    }*/

   /* @Override
    public CategoryFeature deserializeKey(String s, DeserializationContext deserializationContext) throws IOException {
        System.out.println("\n\n "+s+"\n fuck you bitch\n"+deserializationContext);
        return  null;
    }*/
       protected CategoryFeatureDeserializer() {
           super(Map.class);
       }

       @Override
       public Map<CategoryFeature, String> deserialize(JsonParser jsonParser,
                                                DeserializationContext deserializationContext) throws IOException {
           Map<CategoryFeature, String> result = new HashMap<>();
           JsonNode node = jsonParser.getCodec().readTree(jsonParser);
           for (JsonNode element : node) {
               result.put(
                       jsonParser.getCodec().treeToValue(element.get("key"), CategoryFeature.class),
                       jsonParser.getCodec().treeToValue(element.get("value"), String.class)
               );
           }
           return result;
       }


   /* @Override
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


    }*/
}
