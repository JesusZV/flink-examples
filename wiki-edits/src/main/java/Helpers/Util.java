package Helpers;

import org.apache.flink.streaming.connectors.twitter.TwitterSource;

import java.util.Properties;

public class Util {

    /**
     * Gets the required properties to initialize the twitter connector
     * @return required props to use twitter connector
     */
    public Properties getTwitterProps() {

        Properties props = new Properties();
        props.setProperty(TwitterSource.CONSUMER_KEY, Constants.consumerKey);
        props.setProperty(TwitterSource.CONSUMER_SECRET, Constants.consumerSecret);
        props.setProperty(TwitterSource.TOKEN, Constants.twitterToken);
        props.setProperty(TwitterSource.TOKEN_SECRET, Constants.twitterTokenSecret);
        return props;
    }

    /**
     * Gets the required props to initialize the kafka connector
     * @return the required props to use kafka connector
     */
    public Properties getKafkaProps() {
        Properties props = new Properties();
        props.setProperty(Constants.bootstrapservers, Constants.kafkaUrl);
        props.setProperty(Constants.groupId, Constants.kafkaGroupId);
        return props;
    }


}
