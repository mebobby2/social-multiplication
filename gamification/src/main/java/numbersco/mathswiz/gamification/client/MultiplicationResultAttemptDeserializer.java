package numbersco.mathswiz.gamification.client;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import numbersco.mathswiz.gamification.client.dto.MultiplicationResultAttempt;

/**
 * MultiplicationResultAttemptDeserializer
 */
public class MultiplicationResultAttemptDeserializer extends JsonDeserializer<MultiplicationResultAttempt> {
  @Override
  public MultiplicationResultAttempt deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException{
    ObjectCodec oc = jsonParser.getCodec();
    JsonNode node = oc.readTree(jsonParser);
    return new MultiplicationResultAttempt(
      node.get("user").get("alias").asText(),
      node.get("multiplication").get("factorA").asInt(),
      node.get("multiplication").get("factorB").asInt(),
      node.get("resultAttempt").asInt(),
      node.get("correct").asBoolean());
  }
}
