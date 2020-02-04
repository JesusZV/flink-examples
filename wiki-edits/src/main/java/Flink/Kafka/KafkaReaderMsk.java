package Flink.Kafka;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;

import java.util.Properties;

public class KafkaReaderMsk {

    public static void main(String[] args) throws Exception {

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "b-3.awskafkatutorialcluste.hswc5a.c2.kafka.us-west-2.amazonaws.com:9094,b-9.awskafkatutorialcluste.hswc5a.c2.kafka.us-west-2.amazonaws.com:9094,b-1.awskafkatutorialcluste.hswc5a.c2.kafka.us-west-2.amazonaws.com:9094");
        props.setProperty("group.id", "AWSKafkaTutorialTopic");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.addSource(new FlinkKafkaConsumer010<>("AWSKafkaTutorialTopic", new SimpleStringSchema(), props))
                .print();

        env.execute("test");
    }
}
