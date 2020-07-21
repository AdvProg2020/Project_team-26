package client.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CategoryFeatureDeserializer extends StdDeserializer<Map<CategoryFeature, String>> {

    protected CategoryFeatureDeserializer() {
        super(Map.class);
    }

    @Override
    public Map<CategoryFeature, String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
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
}