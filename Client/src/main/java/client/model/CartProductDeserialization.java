package client.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartProductDeserialization extends StdDeserializer<Map<ProductSeller,Integer>> {

    protected CartProductDeserialization() {
        super(Map.class);
    }

    @Override
    public Map<ProductSeller, Integer> deserialize(JsonParser jsonParser,
                                                    DeserializationContext deserializationContext) throws IOException {
        Map<ProductSeller, Integer> result = new HashMap<>();
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        for (JsonNode element : node) {
            result.put(
                    jsonParser.getCodec().treeToValue(element.get("key"), ProductSeller.class),
                    jsonParser.getCodec().treeToValue(element.get("value"), Integer.class)
            );
        }
        return result;
    }
}
