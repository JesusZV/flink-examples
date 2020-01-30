package Flink.Twitter;

import Helpers.Util;
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
 * This class implements the window functions using time windows in order to get how many tweets in each language
 * are happening on each minute
 */
public class NumberOfTweetsPerLanguage {

    public static void main(String[] args) throws Exception {

        Properties props = new Util().getTwitterProps();
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

                        for (Tweet tweet : input) {
                            count++;
                        }
                        out.collect(new Tuple3<>(language, count, new Date(window.getEnd())));
                    }
                })
                .print();


        env.execute("Twitter window");
    }

}
