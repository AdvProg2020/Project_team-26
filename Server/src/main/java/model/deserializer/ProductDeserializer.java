package model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import model.*;
import model.enums.Status;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProductDeserializer extends StdDeserializer<Product> {

    public ProductDeserializer() {
        super(Product.class);
    }

    @Override
    public Product deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ObjectMapper objectMapper = new ObjectMapper();
        int id = (node.get("id")).asInt();
        String name = node.get("name").asText();
        String brand = node.get("brand").asText();
        String description = node.get("description").asText();
        double averageRate = node.get("averageRate").asDouble();
        int amountBought = node.get("amountBought").asInt();
        long price = node.get("price").asLong();
        Category category = objectMapper.treeToValue(node.get("category"), Category.class);
        byte[] image = objectMapper.treeToValue(node.get("image"), byte[].class);
        List<ProductSeller> sellerList = objectMapper.treeToValue(node.get("sellerList"), List.class);
        Map<CategoryFeature, String> categoryFeatures = objectMapper.treeToValue(node.get("categoryFeatures"), Map.class);
        Status status = Status.getStatusFromString(node.get("status").asText());
        return new Product(id,name,brand,description,averageRate,amountBought,price,category,image,sellerList,categoryFeatures,status);
    }
}
