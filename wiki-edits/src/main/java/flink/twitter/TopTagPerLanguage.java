package flink.twitter;

import Helpers.Util;
import Implementations.TweetMap;
import Models.Tweet;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;
import org.apache.flink.util.Collector;
import java.util.Properties;

/**
 *  This example implements reduce methods and time windows to get all the tags that are appearing more often in
 *  each language within a time period of 2 minutes
 */
public class TopTagPerLanguage {

    public static void main(String[] args) throws  Exception{

        Properties props = new Util().getTwitterProps();
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.addSource(new TwitterSource(props))
                //Parse tweets
                .map(new TweetMap())
                // extract Hashtags from tweets
                .flatMap(new FlatMapFunction<Tweet, Tuple3<String, String, Long>>() {
                    @Override
                    public void flatMap(Tweet tweet, Collector<Tuple3<String, String, Long>> collector) throws Exception {
                        for (String tag : tweet.getTags() ) {
                            collector.collect(new Tuple3<>(tweet.getLanguage(), tag, 1L));
                        }
                    }
                }) // DataStream<Tuple3<String, String, Long>>
                // Key by language AND hashtag value
                .keyBy(0, 1)
                .timeWindow(Time.minutes(2))
                // Count how many times each tag was used in tweets by different languages
                .reduce(new ReduceFunction<Tuple3<String, String, Long>>() {
                    @Override
                    public Tuple3<String, String, Long> reduce(Tuple3<String, String, Long> tag1,
                                                               Tuple3<String, String, Long> tag2) throws Exception {
                        return new Tuple3<>(tag1.f0, tag1.f1, tag1.f2 + tag2.f2);
                    }
                })// DataStream<Tuple3<String, String, Long>
                // Group by language and count what is the most popular hashtag per language
                .keyBy(0)
                .timeWindow(Time.minutes(2))
                .reduce(new ReduceFunction<Tuple3<String, String, Long>>() {
                    @Override
                    public Tuple3<String, String, Long> reduce(Tuple3<String, String, Long> tag1,
                                                               Tuple3<String, String, Long> tag2) throws Exception {
                        if (tag1.f2 >= tag2.f2) {
                            return tag1;
                        } else {
                            return tag2;
                        }
                    }
                }) //DataStream<Tuple3<String, String, Long>>
                .print();

        env.execute("Top tag per language");
    }
}
