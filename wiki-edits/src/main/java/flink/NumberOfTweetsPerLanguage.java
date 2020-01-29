package flink;

import Helpers.Constants;
import Implementations.TweetMap;
import Models.Tweet;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;
import org.apache.flink.util.Collector;
import java.util.Date;
import java.util.Properties;

/**
 * In this class i'll try to make an example of the windows usage on Apache Flink
 */
public class NumberOfTweetsPerLanguage {

    public static void main(String[] args) throws Exception {

        Properties props = new Properties();
        props.setProperty(TwitterSource.CONSUMER_KEY, Constants.consumerKey);
        props.setProperty(TwitterSource.CONSUMER_SECRET, Constants.consumerSecret);
        props.setProperty(TwitterSource.TOKEN, Constants.twitterToken);
        props.setProperty(TwitterSource.TOKEN_SECRET, Constants.twitterTokenSecret);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

         env.addSource(new TwitterSource(props))
                .map( new TweetMap())
                .keyBy(new KeySelector<Tweet, String>() {
                    @Override
                    public String getKey(Tweet tweet) throws Exception {
                        return tweet.getLanguage();
                    }
                })
                .timeWindow(Time.minutes(1))
                .apply(new WindowFunction<Tweet, Tuple3<String, Long, Date>, String, TimeWindow>() {
                    @Override
                    public void apply(String language, TimeWindow window, Iterable<Tweet> input,
                                      Collector<Tuple3<String, Long, Date>> out) throws Exception {

                        long count = 0;
                        for (Tweet tweet: input) {
                            count++;
                        }
                        out.collect(new Tuple3<>(language, count, new Date(window.getEnd())));
                    }
                })
                .print();


        env.execute("Twitter window");
    }

}
