package Implementations;

import Models.Person;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implements  the MapFunction Interface the first parameter on <String, Code>
 *  in order to parse the json String from kafka to our model built with our required fields
 */
public  class PersonMap implements MapFunction<org.apache.avro.generic.GenericRecord, Person> {

    static private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Person map(org.apache.avro.generic.GenericRecord kafkaJsonStr) throws Exception {

        //Get the Json Nodes
        JsonNode kafkaJson = mapper.readTree(kafkaJsonStr.toString());
        JsonNode payload = kafkaJson.get("after");
        JsonNode idNode = payload.get("id");
        JsonNode nameNode = payload.get("name");
        JsonNode lastNameNode = payload.get("last_name");

        //Validations for null values
        Integer id  = idNode == null ? 0 : idNode.asInt();
        String name = nameNode == null ? "" : nameNode.asText();
        String lastName = lastNameNode == null ? "" : lastNameNode.asText();
        return new Person(id, name, lastName);
    }
}
