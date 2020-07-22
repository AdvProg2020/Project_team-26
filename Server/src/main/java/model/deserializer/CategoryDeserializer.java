package model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import model.Category;
import model.CategoryFeature;
import model.Product;
import model.ProductSeller;
import model.enums.Status;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CategoryDeserializer extends StdDeserializer<Category> {

    public CategoryDeserializer() {
        super(Map.class);
    }

    @Override
    public Category deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ObjectMapper objectMapper = new ObjectMapper();
        int id = (node.get("id")).asInt();
        String name = node.get("name").asText();
        Category parent = objectMapper.treeToValue(node.get("parent"), Category.class);
        List<CategoryFeature> features = objectMapper.treeToValue(node.get("features"), List.class);
        return new Category(id, name, parent, features);
    }
}
