package Models;

public class Tweet {
    private long   id;
    private String id_str;
    private String name;
    private String screen_name;
    private String location;
    private String url;
    private String description;
    private String translator_type;
    private String verified;
    private Integer followers_count;
    private Integer friends_count;
    private Integer listed_count;
    private String text;
    private String language;

    public Tweet() {

    }

    public Tweet(long id, String id_str, String name, String screen_name, String location, String url,
                String description, String translator_type, String verified, Integer followers_count,
                Integer friends_count, Integer listed_count, String text, String language) {

        this.id = id;
        this.id_str = id_str;
        this.name = name;
        this.screen_name = screen_name;
        this.location = location;
        this.url = url;
        this.description = description;
        this.translator_type = translator_type;
        this.verified = verified;
        this.followers_count = followers_count;
        this.friends_count = friends_count;
        this.listed_count = listed_count;
        this.text = text;
        this.language = language;

    }

    // Getter Methods

    public long getId() {
        return id;
    }

    public String getId_str() {
        return id_str;
    }

    public String getName() {
        return name;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public String getLocation() {
        return location;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getTranslator_type() {
        return translator_type;
    }

    public String getVerified() {
        return verified;
    }

    public Integer getFollowers_count() {
        return followers_count;
    }

    public Integer getFriends_count() {
        return friends_count;
    }

    public Integer getListed_count() {
        return listed_count;
    }

    public String getText() {return text; }

    public String getLanguage() {return language; }

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id  +
                ", id_str = '" + id_str + '\'' +
                ", name = '" + name + '\'' +
                ", screen_name = '" + screen_name + '\'' +
                ", location = '" + location + '\'' +
                ", url = '" + url + '\'' +
                ", description = '" + description + '\'' +
                ", translator_type = '" + translator_type + '\'' +
                ", verified = '" + verified + '\'' +
                ", followers_count = '" + followers_count + '\'' +
                ", friends_count = '" + friends_count + '\'' +
                ", listed_count = '" + listed_count + '\'' +
                ", text = '" + text + '\'' +
                ", language = '" + language + '\'' +
                '}';
    }


}

