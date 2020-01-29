package flink.twitter;

import Helpers.Util;
import Implementations.TweetMap;
import Models.Tweet;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.AllWindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;
import org.apache.flink.util.Collector;
import java.util.Date;
import java.util.Properties;

/**
 *  This class implements the Flatmap and window apply method to find the tags that are appearing more often in a
 *  time period of 20 seconds
 */
public class TopHashTag {

    public static void main(String[] args) throws Exception {

        Properties props = new Util().getTwitterProps();
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.addSource(new TwitterSource(props))
                .map(new TweetMap())
                .flatMap(new FlatMapFunction<Tweet, Tuple2<String, Integer>>() {
                    @Override
                    public void flatMap(Tweet tweet, Collector<Tuple2<String, Integer>> collector) throws Exception {
                        for (String tag: tweet.getTags()) {
                            collector.collect(new Tuple2<>(tag, 1));
                        }
                    }
                })
                .keyBy(0)
                .timeWindow(Time.seconds(20))
                .sum(1)
                .timeWindowAll(Time.seconds(20))
                .apply(new AllWindowFunction<Tuple2<String, Integer>, Tuple3<Date, String, Integer>, TimeWindow>() {

                    @Override
                    public void apply(TimeWindow window, Iterable<Tuple2<String, Integer>> iterable,
                                      Collector<Tuple3<Date, String, Integer>> collector) throws Exception {

                        String topHashTag = "";
                        int count = 0;
                        for (Tuple2<String, Integer> hashtag : iterable ) {
                            if (hashtag.f1 > count){
                                topHashTag = hashtag.f0;
                                count = hashtag.f1;
                            }
                        }
                        collector.collect(new Tuple3<>(new Date(window.getEnd()), topHashTag, count ));
                    }
                })
                .print();

        env.execute("Top HashTag Windows");
    }
}
