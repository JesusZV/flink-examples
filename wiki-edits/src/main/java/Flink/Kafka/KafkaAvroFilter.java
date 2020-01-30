package Flink.Kafka;

import Helpers.Constants;
import Helpers.Util;
import Deserializers.KafkaGenericAvroDeserializationSchema;
import Implementations.PersonMap;
import Models.Person;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import java.util.Properties;

/**
 *  This class shows how to filter a kafka message that is encoded in Avro using schema registry to get the model
 *  it's pending how to get a proper parse to a model, in this example it converts the avro record to a String and it
 *  parses it like if it was a Json
 */
public class KafkaAvroFilter {

    public static void main(String[] args) throws Exception {

        Properties props = new Util().getKafkaProps();

        //Deserialization schema
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        String topic = "conekta.public.person";

        env.addSource(new FlinkKafkaConsumer010<>(topic,
                new KafkaGenericAvroDeserializationSchema(Constants.schemaRegistryUrl), props))
                .map(new PersonMap())
                .filter(new FilterFunction<Person>() {
                    @Override
                    public boolean filter(Person person) throws Exception {
                        return person.getName().contains("jesus");
                    }
                })
                .print();


        env.execute("Kafka Avro filter");

    }
}
