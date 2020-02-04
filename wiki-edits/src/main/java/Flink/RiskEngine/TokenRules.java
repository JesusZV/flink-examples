package Flink.RiskEngine;


import Implementations.TokenMap;
import Models.Token;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SocketTextStreamFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class TokenRules {

    public static void main(String[] args) throws  Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //This DataStream Would be  Converting the Json to a Token Object

        DataStream<Token> stream =
                env.addSource(new SocketTextStreamFunction("localhost",
                        9999,
                        "\n",
                        1))
                        .map(new TokenMap());


        //We can perform independent window operations on the same input in different
        // substreams and then combine the results.

        /*
        // 1- First rule Decline if number of tokens > 5 for this IP in last 10 seconds
        stream
        .flatMap(new FlatMapFunction<Token, Tuple3<String, Integer, String>>() {
            @Override
            public void flatMap(Token token, Collector<Tuple3<String, Integer, String>> collector) throws Exception {
                collector.collect(new Tuple3<>(token.getIpAddress(), 1, token.get_tokenId()));
            }
        })
        .keyBy(0)
        .timeWindow(Time.seconds(5))
        .apply(new WindowFunction<Tuple3<String, Integer, String>, String, Tuple, TimeWindow>() {
            @Override
            public void apply(Tuple tuple, TimeWindow window, Iterable<Tuple3<String, Integer, String>> iterable, Collector<String> out) throws Exception {
                 Integer counter = 0;
                for (Tuple3<String, Integer, String> element: iterable) {
                    counter += element.f1++;

                    if(counter > 5){
                        out.collect("El elemeto ha sido declinado" + element.f2 + counter);
                    }
                }
            }
        })
                .print();

         */



        stream
                .flatMap(new FlatMapFunction<Token, Tuple3<String, Integer, String>>() {
                    @Override
                    public void flatMap(Token token, Collector<Tuple3<String, Integer, String>> collector) throws Exception {
                        collector.collect(new Tuple3<>(token.getIpAddress(), 1, token.get_tokenId()));
                    }
                })
                .keyBy(0)
                .timeWindow(Time.seconds(5))
                .sum(1)
                .filter(new FilterFunction<Tuple3<String, Integer, String>>() {
                    @Override
                    public boolean filter(Tuple3<String, Integer, String> tuple) throws Exception {
                        return tuple.f1 > 5;
                    }
                })
                .writeAsText("output")
                .setParallelism(1);






        /*
        // 2 - Decline if number of tokens > 15 for this IP in last minute
        DataStreamSink<Tuple2<String, Integer>> ip15Minute =
                stream
                        .flatMap(new FlatMapFunction<Token, Tuple2<String, Integer>>() {
                            @Override
                            public void flatMap(Token token, Collector<Tuple2<String, Integer>> collector) throws Exception {
                                collector.collect(new Tuple2<>(token.getIpAddress(), 1));
                            }
                        })
                        .keyBy(0)
                        .timeWindow(Time.minutes(1))
                        .sum(1)
                        .print();


         */



        env.execute("Risk engine");
    }

    /*
    public static class MyProcessWindowFunction extends ProcessWindowFunction<Tuple3<String, Integer, String>, String, String, TimeWindow> {

        @Override
        public void process(String key, Context context, Iterable<Tuple3<String, Integer, String>> input, Collector<String> out) throws Exception {

            Integer count = 0;
            for (Tuple3<String, Integer, String> in: input) {
                count = in.f1++;
            }
            out.collect("Sum" + count);
        }
    }

     */
}

