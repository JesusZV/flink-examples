package Flink.Kafka;

import Helpers.Util;
import Implementations.CodeMap;
import Models.Code;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.util.Collector;

import java.util.*;

/**
 * This example implements a flat map transformation to get each word coming from the status field on topic
 *  conekta.public.codes from CDC
 */
public class KafkaWordCounter {

    public static void main(String[] args) throws Exception {

        Properties props = new Util().getKafkaProps();
        String topicName = "conekta.public.codes";

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.addSource(new FlinkKafkaConsumer010<>(topicName, new SimpleStringSchema(), props))
                .map(new CodeMap())
                .flatMap(new FlatMapFunction<Code, String>() {
                    @Override
                    public void flatMap(Code value, Collector<String> out)
                            throws Exception {
                        for(String word: value.getStatus().split(" ")){
                            out.collect(word);
                        }
                    }
                }).print();

        env.execute("Word Counter");
    }
}
