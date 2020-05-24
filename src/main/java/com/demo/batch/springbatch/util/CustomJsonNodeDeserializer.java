package com.demo.batch.springbatch.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author kaihe
 *
 */

public class CustomJsonNodeDeserializer extends JsonNodeDeserializer {

  @Override
  protected void _handleDuplicateField(JsonParser p, DeserializationContext ctxt,
      JsonNodeFactory nodeFactory, String fieldName, ObjectNode objectNode, JsonNode oldValue,
      JsonNode newValue) throws JsonProcessingException {
    ArrayNode node;
    if (oldValue instanceof ArrayNode) {
      node = (ArrayNode) oldValue;
    } else {
      node = nodeFactory.arrayNode();
      node.add(oldValue);
    }
    node.add(newValue);
    objectNode.set(fieldName, node);
  }
}
