package model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Map;

public class categoryFeatureMapSerialization extends StdSerializer<Map<CategoryFeature,String>> {
    protected categoryFeatureMapSerialization() {
        super(Map.class, true);
    }

    @Override
    public void serialize(Map<CategoryFeature, String> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (Map.Entry<CategoryFeature, String> element : map.entrySet()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField("key", element.getKey());
            jsonGenerator.writeObjectField("value", element.getValue());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }
}
