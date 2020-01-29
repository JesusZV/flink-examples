package flink.twitter;

import Helpers.Util;
import Implementations.TweetMap;
import Models.Tweet;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;

import java.util.Properties;

public class TwitterConnect {

    public static void main(String[] args) throws Exception {

        Properties props = new Util().getTwitterProps();

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSink<Tweet> streamSource = env.addSource(new TwitterSource(props))
                .map( new TweetMap())
                .filter(new FilterFunction<Tweet>() {
                    @Override
                    public boolean filter(Tweet tweet) throws Exception {
                        return tweet.getFollowers_count() != null
                                && tweet.getFollowers_count() > 500
                                && tweet.getLanguage().equals("en");
                    }
                }).print();


        env.execute("Twitter Source");

    }
}
