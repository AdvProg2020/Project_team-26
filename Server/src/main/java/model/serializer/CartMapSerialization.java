package model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import model.ProductSeller;

import java.io.IOException;
import java.util.Map;

public class CartMapSerialization extends StdSerializer<Map<ProductSeller, Integer>> {


    protected CartMapSerialization() {
        super(Map.class, true);
    }

    @Override
    public void serialize(Map<ProductSeller, Integer> productSellerIntegerMap, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (Map.Entry<ProductSeller, Integer> element : productSellerIntegerMap.entrySet()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField("key", element.getKey());
            jsonGenerator.writeObjectField("value", element.getValue());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }
}
