package Implementations;

import Models.Code;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implements  the MapFunction Interface the first parameter on <String, Code>
 *  is the input value, the second is the output value
 */
public  class MapToCode implements MapFunction<String, Code> {

    static private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Code map(String kafkaJsonStr) throws Exception {

        //Get the Json Nodes
        JsonNode kafkaJson = mapper.readTree(kafkaJsonStr);
        JsonNode payload = kafkaJson.get("payload").get("after");
        JsonNode idNode = payload.get("id");
        JsonNode statusNode = payload.get("status");

        //Validations for null values
        Integer id  = idNode == null ? 0 : idNode.asInt();
        String status = statusNode == null ? "" : statusNode.asText();

        return new Code(id, status);
    }
}
