package client.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import net.minidev.json.JSONObject;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.Map;

@JsonComponent
public class ProductSerializer extends StdSerializer<Product> {

    public ProductSerializer() {
        super(Product.class, true);
    }

    @Override
    public void serialize(Product product, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id",product.getId());
        jsonGenerator.writeStringField("name", product.getName());
        jsonGenerator.writeStringField("brand", product.getBrand());
        jsonGenerator.writeStringField("description", product.getDescription());
        jsonGenerator.writeObjectField("category", product.getCategory());
        jsonGenerator.writeObjectField("image", product.getImage());
        jsonGenerator.writeObjectField("sellerList", product.getSellerList());
        jsonGenerator.writeObjectField("categoryFeatures", product.getCategoryFeatures());
        jsonGenerator.writeObjectField("status", product.getStatus());
        jsonGenerator.writeEndObject();
    }
}
