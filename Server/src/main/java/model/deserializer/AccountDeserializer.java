package model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import model.Account;
import model.enums.Role;

import java.io.IOException;
import java.util.Map;

public class AccountDeserializer extends StdDeserializer<Account> {

    public AccountDeserializer() {
        super(Map.class);
    }

    @Override
    public Account deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String username = (node.get("username")).asText();
        String password = node.get("password").asText();
        String email = node.get("email").asText();
        String firstName = node.get("firstName").asText();
        String lastName = node.get("lastName").asText();
        Role role = Role.getRoleFromString(node.get("role").asText());
        String companyName = node.get("companyName").asText();
        long credit = (Long) (node.get("credit")).longValue();
        return new Account(username, password, email, firstName, lastName, role, companyName, credit);
    }
}
