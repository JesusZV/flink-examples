package wikiedits;

import Implementations.TweetMap;
import Models.Tweet;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;

import java.util.Properties;

public class TwitterConnect {

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.setProperty(TwitterSource.CONSUMER_KEY, "qSd5kHU9e4CHRkfcDl7i22NfR");
        props.setProperty(TwitterSource.CONSUMER_SECRET, "UfteRXRoURM8Xaxe59nFsFfvXp61utc4u81HfufOulF2e6OkXB");
        props.setProperty(TwitterSource.TOKEN, "90670132-keD60R8trHJZDwwDHIzISZYfsnLeict4fB1eBTdjv");
        props.setProperty(TwitterSource.TOKEN_SECRET, "yJp33LxHMwoQGahKyh08IYErqQJYbbgaVsc2t2vvIFtnK");

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
