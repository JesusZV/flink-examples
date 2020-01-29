package Implementations;

import Models.Tweet;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TweetMap implements MapFunction<String, Tweet> {

    static private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Tweet map(String tweetJsonStr) throws Exception {

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

            List<String> tags = new ArrayList<>();
            JsonNode entities = tweetJson.get("entities");
            if (entities != null) {
                JsonNode hashtags = entities.get("hashtags");

                for (Iterator<JsonNode> iter = hashtags.elements(); iter.hasNext();) {
                    JsonNode node = iter.next();
                    String hashtag = node.get("text").asText();
                    tags.add(hashtag);
                }
            }

            return new Tweet(id, idStr, name, screenName, location, url, description, translation_type, verified,
                    followersCount, friendsCount, listedCount, text, language);
        }
        return new Tweet();
    }
}