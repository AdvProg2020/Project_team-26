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
import java.util.List;
import java.util.Map;

public class ProductSellerDeserializer extends StdDeserializer<ProductSeller> {

    public ProductSellerDeserializer() {
        super(Map.class);
    }

    @Override
    public ProductSeller deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ObjectMapper objectMapper = new ObjectMapper();
        int id = (node.get("id")).asInt();
        User seller = objectMapper.treeToValue(node.get("seller"), User.class);
        long price = node.get("price").asLong();
        long priceInOff = node.get("priceInOff").asLong();
        int remainingItems = node.get("remainingItems").asInt();
        Status status = Status.getStatusFromString(node.get("status").asText());
        return new ProductSeller(id,seller,price,price,remainingItems,status);
    }
}
