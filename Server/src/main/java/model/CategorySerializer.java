package model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class CategorySerializer extends JsonSerializer<Category> {

    @Override
    public void serialize(Category category, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", category.getId());
        jsonGenerator.writeStringField("name", category.getName());
        if(!category.getParent().equals(category)) {
            jsonGenerator.writeObjectField("parent", category.getParent());
        } else {
            jsonGenerator.writeObjectField("parent", null);
        }
        jsonGenerator.writeObjectField("features", category.getFeatures());
        jsonGenerator.writeEndObject();
    }
}
