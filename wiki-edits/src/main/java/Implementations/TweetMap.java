package Implementations;

import Models.Tweet;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  Class That parses the a twitter json String to a model with our required fields
 */
public class TweetMap implements MapFunction<String, Tweet> {

    static private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Tweet map(String tweetJsonStr) throws Exception {

        long id = 0;
        String idStr = "";
        String name = "";
        String screenName = "";
        String location = "";
        String url = "";
        String description = "";
        String translation_type = "";
        String verified = "";
        int followersCount = 0;
        int friendsCount = 0;
        int listedCount = 0;

        //Get the Json Nodes
        JsonNode tweetJson = mapper.readTree(tweetJsonStr);
        JsonNode userNode = tweetJson.get("user");
        JsonNode textNode = tweetJson.get("text");
        JsonNode languageNode = tweetJson.get("lang");

        if(userNode != null) {
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

             id  = idNode == null ? 0 : idNode.asLong();
             idStr = id_strNode == null ? "" : id_strNode.asText();
             name = nameNode == null ? "" : nameNode.asText();
             screenName = screen_nameNode == null ? "" : screen_nameNode.asText();
             location = locationNode == null  ? "" : locationNode.asText();
             url = urlNode == null ? "" : urlNode.asText();
             description = descriptionNodes == null ? "" : descriptionNodes.asText();
             translation_type = translator_typeNode == null ? "" : translator_typeNode.asText();
             verified = verifiedNode == null ? "" : verifiedNode.asText();
             followersCount = followers_countNodeNode == null ? 0 : followers_countNodeNode.asInt();
             friendsCount = friends_countNode == null ? 0 : friends_countNode.asInt();
             listedCount = listed_countNode == null ? 0 : listed_countNode.asInt();
        }

        String text = textNode == null ? "" : textNode.asText();
        String language = languageNode == null ? "" : languageNode.asText();

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
                    followersCount, friendsCount, listedCount, text, language, tags);
    }
}