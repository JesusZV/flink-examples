package Helpers;

import org.apache.flink.streaming.connectors.twitter.TwitterSource;

import java.util.Properties;

public class Util {

    public Properties getTwitterProps() {

        Properties props = new Properties();
        props.setProperty(TwitterSource.CONSUMER_KEY, Constants.consumerKey);
        props.setProperty(TwitterSource.CONSUMER_SECRET, Constants.consumerSecret);
        props.setProperty(TwitterSource.TOKEN, Constants.twitterToken);
        props.setProperty(TwitterSource.TOKEN_SECRET, Constants.twitterTokenSecret);
        return props;
    }


}
