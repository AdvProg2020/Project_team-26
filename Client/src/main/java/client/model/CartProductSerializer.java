package client.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Map;

public class CartProductSerializer extends StdSerializer<Map<ProductSeller,Integer>> {

    public CartProductSerializer() {
        super(Map.class,true);
    }

    @Override
    public void serialize(Map<ProductSeller, Integer> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (Map.Entry<ProductSeller, Integer> element : map.entrySet()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField("key", element.getKey());
            jsonGenerator.writeObjectField("value", element.getValue());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }
}
