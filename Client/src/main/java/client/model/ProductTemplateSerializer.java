/*package client.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ProductTemplateSerializer extends StdSerializer<ProductTemplate> {

    protected ProductTemplateSerializer(Class<ProductTemplate> t) {
        super(t);
    }

    @Override
    public void serialize(ProductTemplate productTemplate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id",productTemplate.getId());
        jsonGenerator.writeStringField("name", productTemplate.getName());
        jsonGenerator.writeStringField("brand", productTemplate.getBrand());
        jsonGenerator.writeStringField("description", productTemplate.getDescription());
        jsonGenerator.writeObjectField("category", productTemplate.getCategory());
        jsonGenerator.writeObjectField("sellerList", productTemplate.getSellerList());
        jsonGenerator.writeObjectField("categoryFeatures", productTemplate.getCategoryFeatures());
        jsonGenerator.writeObjectField("status", productTemplate.getStatus());
        jsonGenerator.writeEndObject();
    }
}

 */
