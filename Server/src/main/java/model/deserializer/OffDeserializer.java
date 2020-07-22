package model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import model.Account;
import model.Off;
import model.OffItem;
import model.User;
import model.enums.Role;
import model.enums.Status;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OffDeserializer extends StdDeserializer<Off> {

    public OffDeserializer() {
        super(Map.class);
    }
    @Override
    public Off deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ObjectMapper objectMapper = new ObjectMapper();
        int id = (node.get("id")).asInt();
        User seller = objectMapper.treeToValue(node.get("seller"),User.class);
        Date startDate = objectMapper.treeToValue(node.get("startDate"), Date.class);
        Date endDate = objectMapper.treeToValue(node.get("endDate"), Date.class);
        List<OffItem> items = objectMapper.treeToValue(node.get("items"), List.class);
        Status status = Status.getStatusFromString(node.get("status").asText());
        return new Off(id,seller,startDate,endDate,items,status);
    }
}
