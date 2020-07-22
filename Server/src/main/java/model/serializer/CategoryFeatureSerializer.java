package model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import model.CategoryFeature;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
@JsonComponent
public class CategoryFeatureSerializer extends JsonSerializer<CategoryFeature> {
    @Override
    public void serialize(CategoryFeature categoryFeature, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", categoryFeature.getId());
        jsonGenerator.writeStringField("name", categoryFeature.getFeatureName());
        jsonGenerator.writeObjectField("type", categoryFeature.getFeatureType());
        jsonGenerator.writeEndObject();
    }
}
