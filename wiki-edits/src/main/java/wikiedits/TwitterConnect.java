package wikiedits;

import Models.User;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.datastream.DataStream;
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
        DataStreamSink<User> streamSource = env.addSource(new TwitterSource(props))
                .map( new TwitterMap())
                .filter(new FilterFunction<User>() {
                    @Override
                    public boolean filter(User user) throws Exception {
                        return user.getFollowers_count() != null
                                && user.getFollowers_count() > 1000000
                                && user.getLanguage().equals("en");
                    }
                }).print();


        env.execute("Twitter Source");

    }

    public static class TwitterMap implements MapFunction<String, User> {

        static private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public User map(String tweetJsonStr) throws Exception {

            //Get the Json Nodes
            JsonNode tweetJson = mapper.readTree(tweetJsonStr);
            JsonNode userNode = tweetJson.get("user");
            JsonNode textNode = tweetJson.get("text");
            JsonNode languageNode = tweetJson.get("lang");

            if (userNode != null && textNode != null && languageNode != null){

                JsonNode idNode = userNode.get("id");
                JsonNode id_strNode = userNode.get("id_str");
                JsonNode nameNode = userNode.get("name");
                JsonNode screen_nameNode = userNode.get("screen_name");
                JsonNode locationNode = userNode.get("location");
                JsonNode urlNode = userNode.get("url");
                JsonNode descriptionNodes = userNode.get("description");
                JsonNode translator_typeNode = userNode.get("translator_type");
                JsonNode verifiedNode = userNode.get("verified");
                JsonNode followers_countNodeNode = userNode.get("followers_count");
                JsonNode friends_countNode = userNode.get("friends_count");
                JsonNode listed_countNode = userNode.get("listed_count");

                //Test
                long id  = idNode == null ? 0 : idNode.asLong();
                String idStr = id_strNode == null ? "" : id_strNode.asText();
                String name = nameNode == null ? "" : nameNode.asText();
                String screenName = screen_nameNode == null ? "" : screen_nameNode.asText();
                String location = locationNode == null  ? "" : locationNode.asText();
                String url = urlNode == null ? "" : urlNode.asText();
                String description = descriptionNodes == null ? "" : descriptionNodes.asText();
                String translation_type = translator_typeNode == null ? "" : translator_typeNode.asText();
                String verified = verifiedNode == null ? "" : verifiedNode.asText();
                int  followersCount = followers_countNodeNode == null ? 0 : followers_countNodeNode.asInt();
                int friendsCount = friends_countNode == null ? 0 : friends_countNode.asInt();
                int listedCount = listed_countNode == null ? 0 : listed_countNode.asInt();
                String text = textNode.asText();
                String language = languageNode.asText();

                return new User(id, idStr, name, screenName, location, url, description, translation_type, verified,
                        followersCount, friendsCount, listedCount, text, language);
            }
            return new User();
        }
    }
}
