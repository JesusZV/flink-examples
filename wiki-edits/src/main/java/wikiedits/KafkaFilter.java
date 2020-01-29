package wikiedits;

import Helpers.Constants;
import Implementations.CodeMap;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import java.util.Properties;
import Models.Code;

public class KafkaFilter {

    public static void main(String[] args) throws Exception {

        //Required props for kafka connector to start
        Properties properties = new Properties();
        properties.setProperty(Constants.bootstrapservers, Constants.kafkaUrl);
        properties.setProperty(Constants.groupId, Constants.kafkaGroupId);
        String topicName = "conekta.public.codes";

        //Start getting the execution env (Cluster or local)
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();


        env.addSource(new FlinkKafkaConsumer010<>(topicName, new SimpleStringSchema(), properties))
                .map(new CodeMap())
                .filter(new FilterFunction<Code>() {
                    @Override
                    public boolean filter(Code code) {
                        return code.getStatus().contains("c4ca");
                    }
                })
                .print();

        env.execute("Kafka Hello world Transformation");
    }

}
