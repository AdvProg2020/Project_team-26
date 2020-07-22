package client.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import net.minidev.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ProductSerializer extends StdSerializer<Product> {

    public ProductSerializer() {
        super(Map.class, true);
    }

    @Override
    public void serialize(Product product, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id",product.getId());
        jsonGenerator.writeStringField("name", product.getName());
        jsonGenerator.writeStringField("brand", product.getBrand());
        jsonGenerator.writeStringField("description", product.getDescription());
        jsonGenerator.writeNumberField("averageRate", product.getAverageRate());
        jsonGenerator.writeNumberField("amountBought", product.getAmountBought());
        jsonGenerator.writeNumberField("price", product.getPrice());
        jsonGenerator.writeObjectField("category", product.getCategory());
        jsonGenerator.writeObjectField("image", product.getImage());
        jsonGenerator.writeObjectField("sellerList", product.getSellerList());
        jsonGenerator.writeObjectField("categoryFeatures", product.getCategoryFeatures());
        jsonGenerator.writeObjectField("status", product.getStatus());
        jsonGenerator.writeEndObject();
    }
}
