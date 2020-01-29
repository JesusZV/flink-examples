package flink;

import Helpers.Constants;
import Implementations.TweetMap;
import Models.Tweet;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;

import java.util.Properties;

public class TwitterWindow {

    public static void main(String[] args) throws Exception {

        Properties props = new Properties();
        props.setProperty(TwitterSource.CONSUMER_KEY, Constants.consumerKey);
        props.setProperty(TwitterSource.CONSUMER_SECRET, Constants.consumerSecret);
        props.setProperty(TwitterSource.TOKEN, Constants.twitterToken);
        props.setProperty(TwitterSource.TOKEN_SECRET, Constants.twitterTokenSecret);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSink<Tweet> stream = env.addSource(new TwitterSource(props))
                .map( new TweetMap())
                .print();


        env.execute("Twitter window");
    }

}
